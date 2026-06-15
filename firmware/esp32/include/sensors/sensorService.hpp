#ifndef FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP
#define FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP

#include <memory>

#include "appConfig.hpp"
#include "sensors/sensorFactory.hpp"
#include "sensors/sensorTypes.hpp"

namespace sensors {

class SensorService {
public:
    explicit SensorService(const app::AppConfig& config);

    void begin();

    SensorReading read();

private:
    const app::AppConfig& config_;
    SensorFactory sensorFactory_;

    std::unique_ptr<Sensor> temperatureHumiditySensor_;
    std::unique_ptr<Sensor> lightSensor_;
    std::unique_ptr<Sensor> co2Sensor_;
    std::unique_ptr<Sensor> buttonSensor_;
};

}  // namespace sensors

#endif  // FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP