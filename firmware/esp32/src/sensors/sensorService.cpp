#include "sensors/sensor_service.hpp"

#include "sensors/mock_sensor_model.hpp"

namespace sensors {

    SensorService::SensorService(const app::AppConfig& config)
        : config_(config)
    {
    }

    void SensorService::begin()
    {
        // Initialize physical sensor drivers here when replacing the mock source.
    }

    SensorReading SensorService::read() const
    {
        // Mock telemetry for the integration baseline.
        const float elapsedSeconds = millis() / 1000.0f;
        const MockSensorSample sample = MockSensorModel::sampleAt(elapsedSeconds);

        return {
            config_.deviceId,
            config_.sensorId,
            sample.temperature,
            sample.humidity,
        };
    }

}  // namespace sensors
