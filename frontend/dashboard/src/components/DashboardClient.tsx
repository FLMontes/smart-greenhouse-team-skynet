'use client';

import { GreenhouseHeader } from '@/components/GreenhouseHeader';
import { SensorComparisonChart } from '@/components/charts/SensorComparisonChart';
import { HumidityChart } from '@/components/charts/HumidityChart';
import { TemperatureChart } from '@/components/charts/TemperatureChart';
import { LightChart } from '@/components/charts/LightChart';
import { AlertBadge } from '@/components/widgets/AlertBadge';
import { CurrentTemperatureCard } from '@/components/widgets/CurrentTemperatureCard';
import { CurrentHumidityCard } from '@/components/widgets/CurrentHumidityCard';
import { CurrentLightCard } from '@/components/widgets/CurrentLightCard';
import { useSensorPolling } from '@/hooks/useSensorPolling';
import type { Alert, SensorReading } from '@/types/sensor.types';
import { PLACEHOLDER_READINGS } from '@/constants';

const placeholderReading: SensorReading = {
  id: 0,
  sensorId: 'ESP32-01',
  temperature: PLACEHOLDER_READINGS.temperature,
  humidity: PLACEHOLDER_READINGS.humidity,
  light: PLACEHOLDER_READINGS.light,
  co2: 450,
  buttonPressed: false,
  createdAt: new Date().toISOString(),
};

const healthyAlert: Alert = {
  id: 0,
  type: 'SYSTEM_OK',
  sensorId: 'ESP32-01',
  message: 'Todos los sistemas operan normalmente',
  severity: 'low',
  active: true,
  relatedMeasurementId: null,
  triggeredAt: new Date().toISOString(),
};

export function DashboardClient() {
  const { data, latestReading, alerts, loading, error, lastUpdate } = useSensorPolling();
  const currentReading = latestReading ?? data.at(-1) ?? placeholderReading;
  const currentAlert = alerts[0] ?? healthyAlert;
  const isOnline = !error;

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 to-emerald-50 p-6">
      <div className="mx-auto max-w-7xl">
        <GreenhouseHeader isOnline={isOnline} lastUpdate={lastUpdate} />

        {error ? (
          <div className="mb-6 rounded-lg border border-red-200 bg-red-50 px-4 py-3 text-sm font-medium text-red-800">
            No se pudo conectar con el backend: {error.message}
          </div>
        ) : null}

        {loading ? (
          <div className="mb-6 rounded-lg border border-emerald-200 bg-white px-4 py-3 text-sm font-medium text-emerald-800">
            Cargando datos del invernadero...
          </div>
        ) : null}

        <section className="mb-6 grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          <CurrentTemperatureCard
            temperature={currentReading.temperature}
            sensorId={currentReading.sensorId}
            timestamp={currentReading.createdAt}
          />
          <CurrentHumidityCard
            humidity={currentReading.humidity}
            sensorId={currentReading.sensorId}
            timestamp={currentReading.createdAt}
          />
          <CurrentLightCard
            light={currentReading.light}
            sensorId={currentReading.sensorId}
            timestamp={currentReading.createdAt}
          />
          <AlertBadge alert={currentAlert} />
        </section>

        <section className="mb-6 grid gap-4 lg:grid-cols-2">
          <TemperatureChart data={data} />
          <HumidityChart data={data} />
        </section>

        <section className="mb-6 grid gap-4 lg:grid-cols-2">
          <LightChart data={data} />
          <SensorComparisonChart data={data} />
        </section>
      </div>
    </div>
  );
}
