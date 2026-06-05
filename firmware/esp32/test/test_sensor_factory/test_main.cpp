#include <gtest/gtest.h>

#include "sensors/sensorFactory.hpp"

namespace sensors
{
namespace
{

TEST(SensorFactoryTest, CreatesTemperatureSensorFromValidType)
{
    const std::unique_ptr<Sensor> sensor = SensorFactory::createSensor("temperature");

    ASSERT_NE(sensor, nullptr);
    EXPECT_STREQ(sensor->type(), "temperature");
}

TEST(SensorFactoryTest, ReturnsNullForUnknownType)
{
    const std::unique_ptr<Sensor> sensor = SensorFactory::createSensor("unknown");

    EXPECT_EQ(sensor, nullptr);
}

}  // namespace
}  // namespace sensors
