#ifndef FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_CLIENT_HPP
#define FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_CLIENT_HPP

#include <Arduino.h>

#include "appConfig.hpp"
#include "network/networkTypes.hpp"
#include "sensors/sensorTypes.hpp"

namespace network {

class NetworkClient {
public:
    explicit NetworkClient(const app::AppConfig& config);

    void begin();

    void ensureWifiConnection();

    bool isConnected() const;

    bool postSensorReading(const sensors::SensorReading& reading);

    ActuatorState fetchActuatorState();

private:
    void connectToWifi();

    static void logMessage(const String& message);

    const app::AppConfig& config_;
    unsigned long lastWifiRetryAt_;
};

}  // namespace network

#endif  // FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_CLIENT_HPP