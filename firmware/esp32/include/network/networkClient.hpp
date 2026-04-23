#ifndef FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_CLIENT_HPP
#define FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_CLIENT_HPP

#include <Arduino.h>

#include "app_config.hpp"
#include "network/networkTypes.hpp"
#include "sensors/sensorTypes.hpp"

namespace network {

/**
 * @brief Encapsulates Wi-Fi connectivity and backend HTTP communication.
 *
 * This class owns the infrastructure-facing responsibilities of the
 * firmware: connecting to the wireless network, uploading telemetry,
 * and retrieving remote actuator state.
 */
class NetworkClient {
public:
    /**
     * @brief Creates the client with shared firmware configuration.
     *
     * @param config Immutable firmware configuration.
     */
    explicit NetworkClient(const app::AppConfig& config);

    /**
     * @brief Performs initial network setup.
     */
    void begin();

    /**
     * @brief Reconnects to Wi-Fi when the device is offline.
     */
    void ensureWifiConnection();

    /**
     * @brief Indicates whether Wi-Fi is currently available.
     *
     * @return true When the ESP32 is connected to the access point.
     * @return false Otherwise.
     */
    bool isConnected() const;

    /**
     * @brief Sends a telemetry sample to the backend.
     *
     * @param reading Sensor sample to serialize and publish.
     * @return true When the request completes successfully.
     * @return false When the request fails or Wi-Fi is unavailable.
     */
    bool postSensorReading(const sensors::SensorReading& reading);

    /**
     * @brief Fetches the desired LED state from the backend.
     *
     * @return LedState Parsed LED state response.
     */
    LedState fetchLedState();

private:
    /**
     * @brief Opens a Wi-Fi connection using the configured credentials.
     */
    void connectToWifi();

    /**
     * @brief Emits a firmware log line to the serial console.
     *
     * @param message Message body without prefix.
     */
    static void logMessage(const String& message);

    const app::AppConfig& config_;
};

}  // namespace network

#endif  // FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_CLIENT_HPP
