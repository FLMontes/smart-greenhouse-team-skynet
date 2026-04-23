#include <cmath>
#include <gtest/gtest.h>

#include "sensors/mock_sensor_model.hpp"

namespace sensors
{
    namespace
    {

    TEST(MockSensorModelTest, ReturnsExpectedBaselineAtZeroSeconds)
    {
        const MockSensorSample sample = MockSensorModel::sampleAt(0.0f);

        EXPECT_FLOAT_EQ(sample.temperature, 24.5f);
        EXPECT_NEAR(sample.humidity, 48.0f + 9.0f * std::sin(0.8f), 0.0001f);
    }

    TEST(MockSensorModelTest, ProducesValuesInsideExpectedOperatingRange)
    {
        for (float second = 0.0f; second <= 600.0f; second += 15.0f)
        {
            const MockSensorSample sample = MockSensorModel::sampleAt(second);

            EXPECT_GE(sample.temperature, 21.0f);
            EXPECT_LE(sample.temperature, 28.0f);
            EXPECT_GE(sample.humidity, 39.0f);
            EXPECT_LE(sample.humidity, 57.0f);
        }
    }

    TEST(MockSensorModelTest, EvolvesOverTime)
    {
        const MockSensorSample initial = MockSensorModel::sampleAt(10.0f);
        const MockSensorSample later = MockSensorModel::sampleAt(120.0f);

        EXPECT_NE(initial.temperature, later.temperature);
        EXPECT_NE(initial.humidity, later.humidity);
    }

    }  // namespace
}  // namespace sensors
