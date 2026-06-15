/**
 * Dashboard route — a React Server Component. Do **not** add `'use client'`.
 *
 * Composición del dashboard de invernadero inteligente con paneles de sensores actuales,
 * gráficos históricos y alertas.
 */

import { GreenhouseHeader } from '@/components/GreenhouseHeader';
import { SensorComparisonChart } from '@/components/charts/SensorComparisonChart';
import { HumidityChart } from '@/components/charts/HumidityChart';
import { TemperatureChart } from '@/components/charts/TemperatureChart';
import { LightChart } from '@/components/charts/LightChart';
import { AlertBadge } from '@/components/widgets/AlertBadge';
import { CurrentTemperatureCard } from '@/components/widgets/CurrentTemperatureCard';
import { CurrentHumidityCard } from '@/components/widgets/CurrentHumidityCard';
import { CurrentLightCard } from '@/components/widgets/CurrentLightCard';
import type { Alert, SensorReading } from '@/types/sensor.types';
import { PLACEHOLDER_READINGS } from '@/constants';

const placeholderReading: SensorReading = {
  id: 0,
  sensorId: 'SENSOR-001',
  temperature: PLACEHOLDER_READINGS.temperature,
  humidity: PLACEHOLDER_READINGS.humidity,
  light: PLACEHOLDER_READINGS.light,
  createdAt: new Date().toISOString(),
};

const placeholderAlert: Alert = {
  sensorId: 'SENSOR-001',
  message: 'Todos los sistemas operativos normalmente',
  severity: 'low',
  triggeredAt: new Date().toISOString(),
};

export default function DashboardPage() {
  const readings: SensorReading[] = [];
  const now = new Date().toISOString();

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 to-emerald-50 p-6">
      <div className="mx-auto max-w-7xl">
        {/* Header del Invernadero */}
        <GreenhouseHeader isOnline={true} lastUpdate={now} />

        {/* Paneles de Sensores Actuales */}
        <section className="mb-6 grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          <CurrentTemperatureCard
            temperature={placeholderReading.temperature}
            sensorId={placeholderReading.sensorId}
            timestamp={placeholderReading.createdAt}
          />
          <CurrentHumidityCard
            humidity={placeholderReading.humidity}
            sensorId={placeholderReading.sensorId}
            timestamp={placeholderReading.createdAt}
          />
          <CurrentLightCard
            light={placeholderReading.light}
            sensorId={placeholderReading.sensorId}
            timestamp={placeholderReading.createdAt}
          />
          <div>
            <AlertBadge alert={placeholderAlert} />
          </div>
        </section>

        {/* Gráficos Históricos - Fila 1 */}
        <section className="mb-6 grid gap-4 lg:grid-cols-2">
          <TemperatureChart data={readings} />
          <HumidityChart data={readings} />
        </section>

        {/* Gráficos Históricos - Fila 2 */}
        <section className="mb-6 grid gap-4 lg:grid-cols-2">
          <LightChart data={readings} />
          <SensorComparisonChart data={readings} />
        </section>
      </div>
    </div>
  );
}
