#include "network/networkClient.hpp"

#include <ArduinoJson.h>
#include <HTTPClient.h>
#include <WiFi.h>
#include <stdlib.h>

auto constexpr WIFI_CONNECTION_TIMEOUT_MS = 15000UL;
auto constexpr WIFI_RETRY_INTERVAL_MS = 10000UL;
auto constexpr HTTP_REQUEST_TIMEOUT_MS = 5000;
auto constexpr HTTP_STATUS_OK = 200;
auto constexpr HTTP_STATUS_MULTIPLE_CHOICES = 300;

namespace network {

namespace {

uint8_t hexPairToByte(const String& value, int startIndex)
{
    const String pair = value.substring(startIndex, startIndex + 2);
    return static_cast<uint8_t>(strtoul(pair.c_str(), nullptr, 16));
}

bool parseHexColor(const String& color, uint8_t& red, uint8_t& green, uint8_t& blue)
{
    if (color.length() != 7 || color.charAt(0) != '#')
    {
        return false;
    }

    red = hexPairToByte(color, 1);
    green = hexPairToByte(color, 3);
    blue = hexPairToByte(color, 5);

    return true;
}

uint8_t percentToPwmIntensity(int percent)
{
    if (percent < 0)
    {
        percent = 0;
    }

    if (percent > 100)
    {
        percent = 100;
    }

    return static_cast<uint8_t>((percent * 255) / 100);
}

}  // namespace

NetworkClient::NetworkClient(const app::AppConfig& config)
    : config_(config)
    , lastWifiRetryAt_(0)
{
}

void NetworkClient::begin()
{
    WiFi.mode(WIFI_STA);
    WiFi.setAutoReconnect(true);
    WiFi.persistent(false);

    connectToWifi();
}

void NetworkClient::ensureWifiConnection()
{
    if (isConnected())
    {
        return;
    }

    const unsigned long now = millis();
    if (now - lastWifiRetryAt_ < WIFI_RETRY_INTERVAL_MS)
    {
        return;
    }

    lastWifiRetryAt_ = now;
    logMessage("Wi-Fi disconnected. Trying to reconnect...");
    connectToWifi();
}

bool NetworkClient::isConnected() const
{
    return WiFi.status() == WL_CONNECTED;
}

bool NetworkClient::postSensorReading(const sensors::SensorReading& reading)
{
    if (!isConnected())
    {
        logMessage("Telemetry skipped. Wi-Fi is unavailable.");
        return false;
    }

    HTTPClient http;
    const String endpoint = String(config_.backendBaseUrl) + "/api/measurements";

    if (!http.begin(endpoint))
    {
        logMessage("Could not open telemetry endpoint.");
        return false;
    }

    http.setTimeout(HTTP_REQUEST_TIMEOUT_MS);
    http.addHeader("Content-Type", "application/json");

    JsonDocument payload;
    payload["deviceId"] = reading.deviceId;
    payload["sensorId"] = reading.sensorId;
    payload["temperature"] = reading.temperature;
    payload["humidity"] = reading.humidity;
    payload["light"] = reading.light;
    payload["co2"] = reading.co2;
    payload["buttonPressed"] = reading.buttonPressed;

    String body;
    serializeJson(payload, body);

    const int statusCode = http.POST(body);
    const String responseBody = http.getString();
    http.end();

    if (statusCode >= HTTP_STATUS_OK && statusCode < HTTP_STATUS_MULTIPLE_CHOICES)
    {
        logMessage("Telemetry sent.");
        return true;
    }

    logMessage("Telemetry failed. HTTP " + String(statusCode) + " body=" + responseBody);
    return false;
}

ActuatorState NetworkClient::fetchActuatorState()
{
    ActuatorState nextState{
        false,
        false,
        false,
        false,
        false,
        0,
        0,
        0,
        0
    };

    if (!isConnected())
    {
        logMessage("Actuator state fetch skipped. Wi-Fi is unavailable.");
        return nextState;
    }

    HTTPClient http;
    const String endpoint = String(config_.backendBaseUrl) + "/api/actuators/status";

    if (!http.begin(endpoint))
    {
        logMessage("Could not open actuator status endpoint.");
        return nextState;
    }

    http.setTimeout(HTTP_REQUEST_TIMEOUT_MS);

    const int statusCode = http.GET();
    const String responseBody = http.getString();
    http.end();

    if (statusCode < HTTP_STATUS_OK || statusCode >= HTTP_STATUS_MULTIPLE_CHOICES)
    {
        logMessage("Actuator state fetch failed. HTTP " + String(statusCode) + " body=" + responseBody);
        return nextState;
    }

    JsonDocument payload;
    const DeserializationError error = deserializeJson(payload, responseBody);
    if (error)
    {
        logMessage("Actuator state JSON parse failed.");
        return nextState;
    }

    nextState.fanStatus = payload["fanStatus"] | false;
    nextState.buzzerStatus = payload["buzzerStatus"] | false;
    nextState.motorStatus = payload["motorStatus"] | false;
    nextState.resistorStatus = payload["resistorStatus"] | false;

    const String rgbColorCommand = payload["rgbColorCommand"] | "#000000";

    uint8_t red = 0;
    uint8_t green = 0;
    uint8_t blue = 0;

    if (!parseHexColor(rgbColorCommand, red, green, blue))
    {
        logMessage("Invalid RGB color command: " + rgbColorCommand);
        return nextState;
    }

    const int intensityPercent = payload["ledIntensityCommand"] | 0;

    nextState.rgbRed = red;
    nextState.rgbGreen = green;
    nextState.rgbBlue = blue;
    nextState.ledIntensity = percentToPwmIntensity(intensityPercent);

    nextState.known = true;
    return nextState;
}

void NetworkClient::connectToWifi()
{
    if (isConnected())
    {
        return;
    }

    logMessage("Connecting to Wi-Fi...");
    WiFi.disconnect();
    WiFi.begin(config_.wifiSsid, config_.wifiPassword);

    const unsigned long startedAt = millis();
    while (!isConnected() && millis() - startedAt < WIFI_CONNECTION_TIMEOUT_MS)
    {
        delay(500);
        Serial.print('.');
    }

    Serial.println();

    if (isConnected())
    {
        logMessage("Wi-Fi connected. IP: " + WiFi.localIP().toString());
        return;
    }

    logMessage("Wi-Fi connection failed. Will retry later.");
}

void NetworkClient::logMessage(const String& message)
{
    Serial.println(String("[ESP32] ") + message);
}

}  // namespace network