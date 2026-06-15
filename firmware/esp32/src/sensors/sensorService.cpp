#include "sensors/sensorService.hpp"

#include <Arduino.h>
#include <Wire.h>

namespace sensors {

SensorService::SensorService(const app::AppConfig& config)
    : config_(config)
    , htu21dReady_(false)
    , bh1750Ready_(false)
{
}

void SensorService::begin()
{
    Wire.begin(config_.i2cSdaPin, config_.i2cSclPin);

    // Lower I2C speed improves stability on ESP32 with breadboard wiring.
    Wire.setClock(50000);

    pinMode(config_.co2SensorPin, INPUT);

    htu21dReady_ = htu21d_.begin();
    if (htu21dReady_)
    {
        Serial.println("ESP32: HTU21D initialized.");
    }
    else
    {
        Serial.println("ESP32: HTU21D initialization failed.");
    }

    bh1750Ready_ = bh1750_.begin(BH1750::CONTINUOUS_HIGH_RES_MODE);
    if (bh1750Ready_)
    {
        Serial.println("ESP32: BH1750 initialized.");
    }
    else
    {
        Serial.println("ESP32: BH1750 initialization failed.");
    }

    Serial.println("ESP32: MQ-135 analog input initialized.");
}

SensorReading SensorService::read()
{
    return {
        config_.deviceId,
        config_.sensorId,
        readTemperature(),
        readHumidity(),
        readCo2Analog(),
        readLight(),
        false
    };
}

float SensorService::readTemperature()
{
    if (!htu21dReady_)
    {
        return 0.0F;
    }

    return htu21d_.readTemperature();
}

float SensorService::readHumidity()
{
    if (!htu21dReady_)
    {
        return 0.0F;
    }

    return htu21d_.readHumidity();
}

float SensorService::readLight()
{
    if (!bh1750Ready_)
    {
        return 0.0F;
    }

    const float light = bh1750_.readLightLevel();

    if (light < 0.0F)
    {
        return 0.0F;
    }

    return light;
}

float SensorService::readCo2Analog() const
{
    const int rawValue = analogRead(config_.co2SensorPin);

    // Raw MQ-135 ADC value. Calibration is still required for real ppm.
    return static_cast<float>(rawValue);
}

}  // namespace sensors