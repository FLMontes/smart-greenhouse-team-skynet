/**
 * Dashboard route — a React Server Component. Do **not** add `'use client'`.
 *
 * Composición del dashboard de invernadero inteligente con paneles de sensores actuales,
 * gráficos históricos, alertas y tabla de historial.
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
import { sensorService } from '@/services/sensors.service';

// Cambiamos a ruta relativa para obligar a Next.js a encontrar el archivo de la tabla interactiva
import { HistoryTable } from '../../components/HistoryTable'; 

import type { Alert, SensorReading } from '@/types/sensor.types';


const placeholderAlert: Alert = {
  sensorId: 'SENSOR-001',
  message: 'Todos los sistemas operativos normalmente',
  severity: 'low',
  triggeredAt: new Date().toISOString(),
};

export default async function DashboardPage() {
  const readings: SensorReading[] = await sensorService.getSensorReadings(100, 0);
  const latestReading = await sensorService.getLatestReadings();

  const currentReading =
    latestReading ??
    readings[0] ??
    {
      id: 0,
      sensorId: 0,
      temperature: 0,
      humidity: 0,
      light: 0,
      co2: 0,
      timestamp: new Date().toISOString(),
      createdAt: new Date().toISOString(),
      buttonPressed: false,
    };

  const now = currentReading.createdAt;

  return (
    <div className="min-h-screen bg-gradient-to-br from-green-50 to-emerald-50 p-6">
      <div className="mx-auto max-w-7xl">
        <GreenhouseHeader isOnline={true} lastUpdate={now} />

        <section className="mb-6 grid gap-4 md:grid-cols-2 lg:grid-cols-4">
          <CurrentTemperatureCard
            temperature={currentReading.temperature}
            sensorId={String(currentReading.sensorId)}
            timestamp={currentReading.createdAt}
          />

          <CurrentHumidityCard
            humidity={currentReading.humidity}
            sensorId={String(currentReading.sensorId)}
            timestamp={currentReading.createdAt}
          />

          <CurrentLightCard
            light={currentReading.light}
            sensorId={String(currentReading.sensorId)}
            timestamp={currentReading.createdAt}
          />

          <div>
            <AlertBadge alert={placeholderAlert} />
          </div>
        </section>

        <section className="mb-6 grid gap-4 lg:grid-cols-2">
          <TemperatureChart data={readings} />
          <HumidityChart data={readings} />
        </section>

        <section className="mb-6 grid gap-4 lg:grid-cols-2">
          <LightChart data={readings} />
          <SensorComparisonChart data={readings} />
        </section>

        <section className="mt-8">
          <div className="mb-4">
            <h2 className="text-xl font-bold text-gray-800">Registros Históricos Detallados</h2>
            <p className="text-sm text-gray-500">Explora las mediciones tomadas por los sensores de manera tabular.</p>
          </div>
          <HistoryTable />
        </section>
      </div>
    </div>
  );
}