#ifndef FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_FACTORY_HPP
#define FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_FACTORY_HPP

#include <memory>

#include "appConfig.hpp"
#include "sensors/sensorTypes.hpp"

namespace sensors {

class Sensor {
public:
    virtual ~Sensor() = default;

    virtual void begin() = 0;
    virtual void read(SensorReading& reading) = 0;
};

class SensorFactory {
public:
    explicit SensorFactory(const app::AppConfig& config);

    std::unique_ptr<Sensor> createTemperatureHumiditySensor() const;
    std::unique_ptr<Sensor> createLightSensor() const;
    std::unique_ptr<Sensor> createCo2Sensor() const;
    std::unique_ptr<Sensor> createButtonSensor() const;

private:
    const app::AppConfig& config_;
};

}  // namespace sensors

#endif  // FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_FACTORY_HPP