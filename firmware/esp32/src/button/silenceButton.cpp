#include "button/silenceButton.hpp"

namespace button {

SilenceButton::SilenceButton(uint8_t pin)
    : pin_(pin)
{
}

void SilenceButton::begin()
{
    pinMode(pin_, INPUT_PULLUP);
}

bool SilenceButton::isPressed() const
{
    return digitalRead(pin_) == LOW;
}

}