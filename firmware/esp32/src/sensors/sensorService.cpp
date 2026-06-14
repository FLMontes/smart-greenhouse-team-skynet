#include "sensors/sensorService.hpp"
#include <Wire.h>

//#include "sensors/mock_sensor_model.hpp"    //SACAR esta linea cuando reemplacemos los mocks por sensores reales

namespace sensors {

    SensorService::SensorService(const app::AppConfig& config)
        : config_(config)
    {
    }

    void SensorService::begin()
    {
        Wire.begin();

        htu21d_.begin();

        pinMode(config_.silenceButtonPin, INPUT_PULLUP);

        Serial.println("ESP32: HTU21D initialized");
        Serial.println("ESP32: Button input initialized");
    }

    SensorReading SensorService::read()
    {
        const float temperature = htu21d_.readTemperature();
        const float humidity = htu21d_.readHumidity();

        const float co2 = 0.0f;
        const float light = 0.0f;

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
}

