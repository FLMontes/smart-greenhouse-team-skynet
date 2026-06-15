#ifndef FIRMWARE_ESP32_INCLUDE_BUTTON_SILENCE_BUTTON_HPP
#define FIRMWARE_ESP32_INCLUDE_BUTTON_SILENCE_BUTTON_HPP

#include <Arduino.h>

namespace button {

class SilenceButton {
public:
    explicit SilenceButton(uint8_t pin);

    void begin();

    bool isPressed() const;

private:
    uint8_t pin_;
};

}  // namespace button

#endif  // FIRMWARE_ESP32_INCLUDE_BUTTON_SILENCE_BUTTON_HPP