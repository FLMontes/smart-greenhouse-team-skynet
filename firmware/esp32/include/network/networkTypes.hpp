#ifndef FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_TYPES_HPP
#define FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_TYPES_HPP

namespace network {

/**
 * @brief Result of the LED control query against the backend.
 */
struct LedState {
  bool enabled;
  bool known;
};

}  // namespace network

#endif  // FIRMWARE_ESP32_INCLUDE_NETWORK_NETWORK_TYPES_HPP
