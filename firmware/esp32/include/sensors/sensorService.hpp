#ifndef FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP
#define FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP

#include <Adafruit_HTU21DF.h>
#include <BH1750.h>
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
    Adafruit_HTU21DF htu21d_;
    BH1750 bh1750_;

    bool htu21dReady_;
    bool bh1750Ready_;

    float readTemperature();
    float readHumidity();
    float readLight();
    float readCo2Analog() const;
};

}  // namespace sensors

#endif  // FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_SERVICE_HPP