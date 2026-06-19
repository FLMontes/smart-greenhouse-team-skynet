#include "sensors/sensorFactory.hpp"

#include <Arduino.h>
#include <Adafruit_HTU21DF.h>
#include <BH1750.h>

namespace sensors {

namespace {

class TemperatureHumiditySensor final : public Sensor {
public:
    void begin() override
    {
        ready_ = htu21d_.begin();

        if (ready_)
        {
            Serial.println("ESP32: HTU21D initialized.");
        }
        else
        {
            Serial.println("ESP32: HTU21D initialization failed.");
        }
    }

    void read(SensorReading& reading) override
    {
        if (!ready_)
        {
            reading.temperature = 0.0F;
            reading.humidity = 0.0F;
            return;
        }

        reading.temperature = htu21d_.readTemperature();
        reading.humidity = htu21d_.readHumidity();
    }

private:
    Adafruit_HTU21DF htu21d_;
    bool ready_ = false;
};

class LightSensor final : public Sensor {
public:
    void begin() override
    {
        ready_ = bh1750_.begin(BH1750::CONTINUOUS_HIGH_RES_MODE);

        if (ready_)
        {
            Serial.println("ESP32: BH1750 initialized.");
        }
        else
        {
            Serial.println("ESP32: BH1750 initialization failed.");
        }
    }

    void read(SensorReading& reading) override
    {
        if (!ready_)
        {
            reading.light = 0.0F;
            return;
        }

        const float light = bh1750_.readLightLevel();
        reading.light = light < 0.0F ? 0.0F : light;
    }

private:
    BH1750 bh1750_;
    bool ready_ = false;
};

class Co2Sensor final : public Sensor {
public:
    explicit Co2Sensor(uint8_t pin)
        : pin_(pin)
    {
    }

    void begin() override
    {
        pinMode(pin_, INPUT);
        Serial.println("ESP32: MQ-135 analog input initialized.");
    }

    void read(SensorReading& reading) override
    {
        const int rawValue = analogRead(pin_);

        reading.co2 = static_cast<float>(rawValue);
    }

private:
    uint8_t pin_;
};

class ButtonSensor final : public Sensor {
public:
    explicit ButtonSensor(uint8_t pin)
        : pin_(pin)
    {
    }

    void begin() override
    {
        pinMode(pin_, INPUT_PULLUP);
        Serial.println("ESP32: Silence button initialized.");
    }

    void read(SensorReading& reading) override
    {
        reading.buttonPressed = digitalRead(pin_) == LOW;
    }

private:
    uint8_t pin_;
};

}  // namespace

SensorFactory::SensorFactory(const app::AppConfig& config)
    : config_(config)
{
}

std::unique_ptr<Sensor> SensorFactory::createTemperatureHumiditySensor() const
{
    return std::unique_ptr<Sensor>(new TemperatureHumiditySensor());
}

std::unique_ptr<Sensor> SensorFactory::createLightSensor() const
{
    return std::unique_ptr<Sensor>(new LightSensor());
}

std::unique_ptr<Sensor> SensorFactory::createCo2Sensor() const
{
    return std::unique_ptr<Sensor>(new Co2Sensor(config_.co2SensorPin));
}

std::unique_ptr<Sensor> SensorFactory::createButtonSensor() const
{
    return std::unique_ptr<Sensor>(new ButtonSensor(config_.silenceButtonPin));
}

}  // namespace sensors