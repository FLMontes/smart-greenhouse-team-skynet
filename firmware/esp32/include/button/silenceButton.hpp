#ifndef SILENCE_BUTTON_HPP
#define SILENCE_BUTTON_HPP

#include <Arduino.h>

namespace button {

class SilenceButton
{
public:
    explicit SilenceButton(uint8_t pin);

    void begin();

    bool isPressed() const;

private:
    uint8_t pin_;
};

}

#endif