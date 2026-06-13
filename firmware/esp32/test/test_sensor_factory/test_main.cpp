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

TEST(SensorFactoryTest, CreatesCo2SensorFromValidType)
{
    const auto sensor =
        SensorFactory::createSensor("co2");

    ASSERT_NE(sensor, nullptr);
    EXPECT_STREQ(sensor->type(), "co2");
}

TEST(SensorFactoryTest, ReturnsNullForUnknownType)
{
    const std::unique_ptr<Sensor> sensor = SensorFactory::createSensor("unknown");

    EXPECT_EQ(sensor, nullptr);
}

}  // namespace
}  // namespace sensors

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
