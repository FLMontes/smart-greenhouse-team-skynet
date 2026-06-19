#ifndef FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_TYPES_HPP
#define FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_TYPES_HPP

#include <Arduino.h>

namespace sensors {

/**
 * @brief Sensor payload sent by the ESP32 to the backend.
 *
 * CO2 is currently sent as the raw MQ-135 ADC value.
 */
struct SensorReading {
    String deviceId;
    long sensorId;
    float temperature;
    float humidity;
    float co2;
    float light;
    bool buttonPressed;
};

}  // namespace sensors

#endif  // FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_TYPES_HPP