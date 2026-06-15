#include "sensors/sensorService.hpp"

#include <Wire.h>

namespace sensors {

SensorService::SensorService(const app::AppConfig& config)
    : config_(config)
    , sensorFactory_(config)
    , temperatureHumiditySensor_(sensorFactory_.createTemperatureHumiditySensor())
    , lightSensor_(sensorFactory_.createLightSensor())
    , co2Sensor_(sensorFactory_.createCo2Sensor())
    , buttonSensor_(sensorFactory_.createButtonSensor())
{
}

void SensorService::begin()
{
    Wire.begin(config_.i2cSdaPin, config_.i2cSclPin);

    // Lower I2C speed improves stability on ESP32 with breadboard wiring.
    Wire.setClock(50000);

    temperatureHumiditySensor_->begin();
    lightSensor_->begin();
    co2Sensor_->begin();
    buttonSensor_->begin();
}

SensorReading SensorService::read()
{
    SensorReading reading{
        config_.deviceId,
        config_.sensorId,
        0.0F,
        0.0F,
        0.0F,
        0.0F,
        false
    };

    temperatureHumiditySensor_->read(reading);
    lightSensor_->read(reading);
    co2Sensor_->read(reading);
    buttonSensor_->read(reading);

    return reading;
}

}  // namespace sensors