#include "network/networkClient.hpp"

#include <ArduinoJson.h>
#include <HTTPClient.h>
#include <WiFi.h>

auto constexpr WIFI_CONNECTION_TIMEOUT_MS = 15000UL;
auto constexpr WIFI_RETRY_INTERVAL_MS = 10000UL;
auto constexpr HTTP_STATUS_OK = 200;
auto constexpr HTTP_STATUS_MULTIPLE_CHOICES = 300;

namespace network {

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

        http.addHeader("Content-Type", "application/json");

        JsonDocument payload;
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
            logMessage("Telemetry sent. temp=" + String(reading.temperature, 1) + "C humidity=" +
                       String(reading.humidity, 1) + "%");
            return true;
        }

        logMessage("Telemetry failed. HTTP " + String(statusCode) + " body=" + responseBody);
        return false;
    }

    LedState NetworkClient::fetchLedState()
    {
        LedState nextState{false, false};

        if (!isConnected())
        {
            logMessage("LED state fetch skipped. Wi-Fi is unavailable.");
            return nextState;
        }

        HTTPClient http;
        const String endpoint = String(config_.backendBaseUrl) + "/devices/" + config_.deviceId + "/led";

        if (!http.begin(endpoint))
        {
            logMessage("Could not open LED control endpoint.");
            return nextState;
        }

        const int statusCode = http.GET();
        const String responseBody = http.getString();
        http.end();

        if (statusCode < HTTP_STATUS_OK || statusCode >= HTTP_STATUS_MULTIPLE_CHOICES)
        {
            logMessage("LED state fetch failed. HTTP " + String(statusCode) + " body=" + responseBody);
            return nextState;
        }

        JsonDocument payload;
        const DeserializationError error = deserializeJson(payload, responseBody);
        if (error)
        {
            logMessage("LED state JSON parse failed.");
            return nextState;
        }

        nextState.enabled = payload["enabled"] | false;
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