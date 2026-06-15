#include <Arduino.h>
#include <Adafruit_NeoPixel.h>

#include "appConfig.hpp"
#include "button/silenceButton.hpp"
#include "network/networkClient.hpp"
#include "network/networkTypes.hpp"
#include "sensors/sensorService.hpp"

auto constexpr SERIAL_BAUD_RATE = 115200;
auto constexpr DELAY_BETWEEN_TASKS_MS = 100;
auto constexpr LED_STRIP_PIXEL_COUNT = 8;

namespace {

network::NetworkClient networkClient(app::CONFIG);
sensors::SensorService sensorService(app::CONFIG);
button::SilenceButton silenceButton(app::CONFIG.silenceButtonPin);

Adafruit_NeoPixel ledStrip(
    LED_STRIP_PIXEL_COUNT,
    app::CONFIG.ledStripPin,
    NEO_GRB + NEO_KHZ800
);

unsigned long lastTelemetryAt = 0;
unsigned long lastActuatorPollAt = 0;
network::ActuatorState currentActuatorState{false, false, false, false, false, 0, 0, 0, 0};

void applyRgb(uint8_t red, uint8_t green, uint8_t blue)
{
    analogWrite(app::CONFIG.rgbRedPin, red);
    analogWrite(app::CONFIG.rgbGreenPin, green);
    analogWrite(app::CONFIG.rgbBluePin, blue);
}

void applyLedStrip(uint8_t red, uint8_t green, uint8_t blue, uint8_t intensity)
{
    ledStrip.setBrightness(intensity);

    for (uint16_t i = 0; i < ledStrip.numPixels(); ++i)
    {
        ledStrip.setPixelColor(i, ledStrip.Color(red, green, blue));
    }

    ledStrip.show();
}

void applyDigitalOutput(uint8_t pin, bool enabled)
{
    digitalWrite(pin, enabled ? HIGH : LOW);
}

void applyActuatorState(const network::ActuatorState& actuatorState)
{
    applyRgb(actuatorState.rgbRed, actuatorState.rgbGreen, actuatorState.rgbBlue);

    applyLedStrip(
        actuatorState.rgbRed,
        actuatorState.rgbGreen,
        actuatorState.rgbBlue,
        actuatorState.ledIntensity
    );

    applyDigitalOutput(app::CONFIG.fanPin, actuatorState.fanStatus);
    applyDigitalOutput(app::CONFIG.resistorPin, actuatorState.resistorStatus);
    applyDigitalOutput(app::CONFIG.motorPin, actuatorState.motorStatus);
    applyDigitalOutput(app::CONFIG.buzzerPin, actuatorState.buzzerStatus);
}

void setupOutputPins()
{
    pinMode(app::CONFIG.rgbRedPin, OUTPUT);
    pinMode(app::CONFIG.rgbGreenPin, OUTPUT);
    pinMode(app::CONFIG.rgbBluePin, OUTPUT);

    pinMode(app::CONFIG.fanPin, OUTPUT);
    pinMode(app::CONFIG.resistorPin, OUTPUT);
    pinMode(app::CONFIG.motorPin, OUTPUT);
    pinMode(app::CONFIG.buzzerPin, OUTPUT);

    applyRgb(0, 0, 0);

    digitalWrite(app::CONFIG.fanPin, LOW);
    digitalWrite(app::CONFIG.resistorPin, LOW);
    digitalWrite(app::CONFIG.motorPin, LOW);
    digitalWrite(app::CONFIG.buzzerPin, LOW);
}

void handleTelemetryTask()
{
    if (millis() - lastTelemetryAt < app::CONFIG.telemetryIntervalMs)
    {
        return;
    }

    lastTelemetryAt = millis();
    networkClient.ensureWifiConnection();

    if (!networkClient.isConnected())
    {
        Serial.println("ESP32: Skipping telemetry because Wi-Fi is offline.");
        return;
    }

    const sensors::SensorReading reading = sensorService.read();
    networkClient.postSensorReading(reading);
}

void handleActuatorPollingTask()
{
    if (millis() - lastActuatorPollAt < app::CONFIG.actuatorPollIntervalMs)
    {
        return;
    }

    lastActuatorPollAt = millis();
    networkClient.ensureWifiConnection();

    if (!networkClient.isConnected())
    {
        Serial.println("ESP32: Skipping actuator polling because Wi-Fi is offline.");
        return;
    }

    const network::ActuatorState nextState = networkClient.fetchActuatorState();
    if (!nextState.known)
    {
        return;
    }

    applyActuatorState(nextState);
    currentActuatorState = nextState;

    Serial.println(
        String("ESP32: Actuators updated. RGB=(") +
        String(nextState.rgbRed) + "," +
        String(nextState.rgbGreen) + "," +
        String(nextState.rgbBlue) + "), strip brightness=" +
        String(nextState.ledIntensity) +
        ", fan=" +
        String(nextState.fanStatus ? "ON" : "OFF") +
        ", heater=" +
        String(nextState.resistorStatus ? "ON" : "OFF") +
        ", pump=" +
        String(nextState.motorStatus ? "ON" : "OFF") +
        ", buzzer=" +
        String(nextState.buzzerStatus ? "ON" : "OFF")
    );
}

}  // namespace

void setup()
{
    Serial.begin(SERIAL_BAUD_RATE);

    delay(DELAY_BETWEEN_TASKS_MS * 10);
    Serial.println("[ESP32] Booting firmware...");

    setupOutputPins();

    ledStrip.begin();
    ledStrip.clear();
    ledStrip.show();

    silenceButton.begin();
    sensorService.begin();
    networkClient.begin();
}

void loop()
{
    handleTelemetryTask();
    handleActuatorPollingTask();

    if (silenceButton.isPressed())
    {
        Serial.println("[ESP32] Alarm silence requested");
    }

    delay(DELAY_BETWEEN_TASKS_MS);
}