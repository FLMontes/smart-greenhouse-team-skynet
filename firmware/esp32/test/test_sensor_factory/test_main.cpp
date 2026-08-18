#include <gtest/gtest.h>

#include <memory>
#include <type_traits>
#include <utility>

#include "sensors/sensorFactory.hpp"

namespace sensors {
namespace {

using SensorPointer = std::unique_ptr<Sensor>;

TEST(SensorFactoryContractTest, IsConstructibleWithApplicationConfig)
{
    EXPECT_TRUE(
        (std::is_constructible_v<SensorFactory, const app::AppConfig&>)
    );
}

TEST(SensorFactoryContractTest, ExposesExpectedCreationMethods)
{
    EXPECT_TRUE(
        (std::is_same_v<
            decltype(std::declval<const SensorFactory&>()
                         .createTemperatureHumiditySensor()),
            SensorPointer>)
    );

    EXPECT_TRUE(
        (std::is_same_v<
            decltype(std::declval<const SensorFactory&>()
                         .createLightSensor()),
            SensorPointer>)
    );

    EXPECT_TRUE(
        (std::is_same_v<
            decltype(std::declval<const SensorFactory&>()
                         .createCo2Sensor()),
            SensorPointer>)
    );

    EXPECT_TRUE(
        (std::is_same_v<
            decltype(std::declval<const SensorFactory&>()
                         .createButtonSensor()),
            SensorPointer>)
    );
}

}  // namespace
}  // namespace sensors

int main(int argc, char** argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}