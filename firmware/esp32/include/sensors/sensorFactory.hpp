#ifndef FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_FACTORY_HPP
#define FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_FACTORY_HPP

#include <cstring>
#include <memory>

namespace sensors
{

class Sensor
{
public:
    virtual ~Sensor() = default;

    virtual const char* type() const = 0;
};

class TemperatureSensor : public Sensor
{
public:
    const char* type() const override
    {
        return "temperature";
    }
};

class HumiditySensor : public Sensor
{
public:
    const char* type() const override
    {
        return "humidity";
    }
};

class SensorFactory
{
public:
    static std::unique_ptr<Sensor> createSensor(const char* type)
    {
        if (type == nullptr)
        {
            return nullptr;
        }

        if (std::strcmp(type, "temperature") == 0)
        {
            return std::unique_ptr<Sensor>(new TemperatureSensor());
        }

        if (std::strcmp(type, "humidity") == 0)
        {
            return std::unique_ptr<Sensor>(new HumiditySensor());
        }

        return nullptr;
    }
};

}  // namespace sensors

#endif  // FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_FACTORY_HPP
