#ifndef FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_TYPES_HPP
#define FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_TYPES_HPP

#include <stdint.h>

namespace network {

struct ActuatorState {
    bool known;

    bool fanStatus;
    bool buzzerStatus;
    bool motorStatus;
    bool resistorStatus;

    uint8_t rgbRed;
    uint8_t rgbGreen;
    uint8_t rgbBlue;

    uint8_t ledIntensity;
};

}  // namespace network

#endif  // FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_TYPES_HPP