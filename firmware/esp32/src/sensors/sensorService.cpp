#include "sensors/sensorService.hpp"

#include <Arduino.h>
#include <Wire.h>

namespace sensors {

SensorService::SensorService(const app::AppConfig& config)
    : config_(config)
{
}

void SensorService::begin()
{
    Wire.begin(config_.i2cSdaPin, config_.i2cSclPin);

    pinMode(config_.co2SensorPin, INPUT);

    htu21d_.begin();
    Serial.println("ESP32: HTU21D initialized");

    if (!bh1750_.begin(BH1750::CONTINUOUS_HIGH_RES_MODE))
    {
        Serial.println("ESP32: BH1750 initialization failed");
    }
    else
    {
        Serial.println("ESP32: BH1750 initialized");
    }

    Serial.println("ESP32: MQ-135 analog input initialized");
}

SensorReading SensorService::read()
{
    const float temperature = htu21d_.readTemperature();
    const float humidity = htu21d_.readHumidity();
    const float light = bh1750_.readLightLevel();
    const float co2 = readCo2Analog();

    const bool buttonPressed = digitalRead(config_.silenceButtonPin) == LOW;

    return {
        config_.deviceId,
        config_.sensorId,
        temperature,
        humidity,
        co2,
        light,
        buttonPressed
    };
}

float SensorService::readCo2Analog() const
{
    const int rawValue = analogRead(config_.co2SensorPin);

    // Raw MQ-135 value. Calibration is still required.
    return static_cast<float>(rawValue);
}

}  // namespace sensors