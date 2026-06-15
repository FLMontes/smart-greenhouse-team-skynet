#ifndef FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP
#define FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP

#include <BH1750.h>
#include <SparkFunHTU21D.h>
#include <Wire.h>

#include "appConfig.hpp"
#include "sensors/sensorTypes.hpp"

namespace sensors {

class SensorService {
public:
    explicit SensorService(const app::AppConfig& config);

    void begin();

    SensorReading read();

private:
    const app::AppConfig& config_;
    HTU21D htu21d_;
    BH1750 bh1750_;

    float readCo2Analog() const;
};

}  // namespace sensors

#endif  // FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP