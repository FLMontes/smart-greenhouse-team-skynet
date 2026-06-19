#include <Arduino.h>

#include "appConfig.hpp"
#include "network/networkClient.hpp"
#include "network/networkTypes.hpp"
#include "sensors/sensorService.hpp"

auto constexpr SERIAL_BAUD_RATE = 115200;
auto constexpr DELAY_BETWEEN_TASKS_MS = 100;

namespace {

network::NetworkClient networkClient(app::CONFIG);
sensors::SensorService sensorService(app::CONFIG);

unsigned long lastTelemetryAt = 0;
unsigned long lastActuatorPollingAt = 0;

void configureOutputs()
{
    pinMode(app::CONFIG.rgbRedPin, OUTPUT);
    pinMode(app::CONFIG.rgbGreenPin, OUTPUT);
    pinMode(app::CONFIG.rgbBluePin, OUTPUT);

    pinMode(app::CONFIG.ledStripPin, OUTPUT);
    pinMode(app::CONFIG.fanPin, OUTPUT);
    pinMode(app::CONFIG.heaterPin, OUTPUT);
    pinMode(app::CONFIG.pumpPin, OUTPUT);
    pinMode(app::CONFIG.buzzerPin, OUTPUT);
}

void turnOffOutputs()
{
    digitalWrite(app::CONFIG.rgbRedPin, LOW);
    digitalWrite(app::CONFIG.rgbGreenPin, LOW);
    digitalWrite(app::CONFIG.rgbBluePin, LOW);

    digitalWrite(app::CONFIG.ledStripPin, LOW);
    digitalWrite(app::CONFIG.fanPin, LOW);
    digitalWrite(app::CONFIG.heaterPin, LOW);
    digitalWrite(app::CONFIG.pumpPin, LOW);
    digitalWrite(app::CONFIG.buzzerPin, LOW);
}

void applyRgbCommand(uint8_t red, uint8_t green, uint8_t blue)
{
    digitalWrite(app::CONFIG.rgbRedPin, red > 0 ? HIGH : LOW);
    digitalWrite(app::CONFIG.rgbGreenPin, green > 0 ? HIGH : LOW);
    digitalWrite(app::CONFIG.rgbBluePin, blue > 0 ? HIGH : LOW);
}

void applyLightCommand(uint8_t ledIntensity)
{
    // Current demo hardware uses the lighting output as ON/OFF.
    // Any non-zero intensity command turns the lighting output on.
    digitalWrite(app::CONFIG.ledStripPin, ledIntensity > 0 ? HIGH : LOW);
}

void applyActuatorState(const network::ActuatorState& actuatorState)
{
    if (!actuatorState.known)
    {
        Serial.println("ESP32: Actuator state unknown. Outputs were not updated.");
        return;
    }

    digitalWrite(app::CONFIG.fanPin, actuatorState.fanStatus ? HIGH : LOW);
    digitalWrite(app::CONFIG.heaterPin, actuatorState.resistorStatus ? HIGH : LOW);
    digitalWrite(app::CONFIG.pumpPin, actuatorState.motorStatus ? HIGH : LOW);
    digitalWrite(app::CONFIG.buzzerPin, actuatorState.buzzerStatus ? HIGH : LOW);

    applyRgbCommand(
        actuatorState.rgbRed,
        actuatorState.rgbGreen,
        actuatorState.rgbBlue
    );

    applyLightCommand(actuatorState.ledIntensity);

    Serial.println(
        String("ESP32: Actuators updated. fan=") + String(actuatorState.fanStatus) +
        " heater=" + String(actuatorState.resistorStatus) +
        " pump=" + String(actuatorState.motorStatus) +
        " buzzer=" + String(actuatorState.buzzerStatus) +
        " ledIntensity=" + String(static_cast<int>(actuatorState.ledIntensity))
    );
}

void handleTelemetryTask()
{
    if (millis() - lastTelemetryAt < app::CONFIG.telemetryIntervalMs)
    {
        return;
    }

    lastTelemetryAt = millis();

    sensors::SensorReading reading = sensorService.read();

    Serial.println(
        String("ESP32: Sensors read. temp=") + String(reading.temperature, 1) +
        " humidity=" + String(reading.humidity, 1) +
        " light=" + String(reading.light, 1) +
        " co2=" + String(reading.co2, 0) +
        " buttonPressed=" + String(reading.buttonPressed)
    );

    networkClient.ensureWifiConnection();

    if (!networkClient.isConnected())
    {
        Serial.println("ESP32: Skipping telemetry because Wi-Fi is disconnected.");
        return;
    }

    if (networkClient.postSensorReading(reading))
    {
        Serial.println("ESP32: Measurement sent successfully.");
    }
    else
    {
        Serial.println("ESP32: Failed to send measurement.");
    }
}

void handleActuatorPollingTask()
{
    if (millis() - lastActuatorPollingAt < app::CONFIG.actuatorPollingIntervalMs)
    {
        return;
    }

    lastActuatorPollingAt = millis();

    networkClient.ensureWifiConnection();

    if (!networkClient.isConnected())
    {
        Serial.println("ESP32: Skipping actuator polling because Wi-Fi is disconnected.");
        return;
    }

    const network::ActuatorState actuatorState = networkClient.fetchActuatorState();
    applyActuatorState(actuatorState);
}

} // namespace

void setup()
{
    Serial.begin(SERIAL_BAUD_RATE);
    delay(500);

    sensorService.begin();

    configureOutputs();
    turnOffOutputs();

    networkClient.begin();

    Serial.println("ESP32: Firmware initialized.");
}

void loop()
{
    handleTelemetryTask();
    handleActuatorPollingTask();

    delay(DELAY_BETWEEN_TASKS_MS);
}