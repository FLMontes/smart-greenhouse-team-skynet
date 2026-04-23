#ifndef FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_TYPES_HPP
#define FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_TYPES_HPP

#include <Arduino.h>

namespace sensors {

/**
 * @brief Normalized sensor payload sent by the device.
 *
 * This structure represents a single telemetry sample ready to be
 * serialized and published to the backend service.
 */
struct SensorReading {
  String deviceId;
  String sensorId;
  float temperature;
  float humidity;
};

}  // namespace sensors

#endif  // FIRMWARE_ESP32_INCLUDE_SENSORS_SENSOR_TYPES_HPP
