#ifndef FIRMWARE_ESP32_INCLUDE_APP_CONFIG_HPP
#define FIRMWARE_ESP32_INCLUDE_APP_CONFIG_HPP

#include <stdint.h>

namespace app {

struct AppConfig {
    const char* wifiSsid;
    const char* wifiPassword;
    const char* backendBaseUrl;
    const char* deviceId;
    long sensorId;

    uint8_t co2SensorPin;
    uint8_t silenceButtonPin;

    uint8_t i2cSdaPin;
    uint8_t i2cSclPin;

    uint8_t rgbRedPin;
    uint8_t rgbGreenPin;
    uint8_t rgbBluePin;

    uint8_t ledStripPin;
    uint8_t fanPin;
    uint8_t heaterPin;
    uint8_t pumpPin;
    uint8_t buzzerPin;

    unsigned long telemetryIntervalMs;
    unsigned long actuatorPollingIntervalMs;
};

static constexpr AppConfig CONFIG{
    "your_wifi_name",
    "your_wifi_password",
    "http://192.168.0.100:8080",
    "esp32-greenhouse-01",
    1,

    34,
    14,

    21,
    22,

    25,
    26,
    27,

    33,
    16,
    17,
    18,
    19,

    2000UL,
    1000UL
};

}  // namespace app

#endif  // FIRMWARE_ESP32_INCLUDE_APP_CONFIG_HPP