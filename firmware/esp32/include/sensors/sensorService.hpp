#ifndef FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP
#define FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP

#include "app_config.hpp"
#include "sensors/sensorTypes.hpp"

namespace sensors {

/**
 * @brief Provides sensor lifecycle management and reading acquisition.
 *
 * The current implementation produces mock telemetry values for
 * integration purposes. A real sensor driver can replace the
 * implementation without affecting the rest of the application.
 */
class SensorService {
public:
    /**
     * @brief Creates the service with the shared application configuration.
     *
     * @param config Immutable firmware configuration.
     */
    explicit SensorService(const app::AppConfig& config);

    /**
     * @brief Initializes the sensor subsystem.
     */
    void begin();

    /**
     * @brief Reads the current sensor values.
     *
     * @return SensorReading Telemetry sample ready to publish.
     */
    SensorReading read() const;

private:
    const app::AppConfig& config_;
};

}  // namespace sensors

#endif  // FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP
