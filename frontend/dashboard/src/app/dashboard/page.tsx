/**
 * Dashboard route — a React Server Component. Do **not** add `'use client'`.
 *
 * Responsible for page layout and composition only: no `fetch()` or polling here.
 * Import chart and widget Client Components and arrange them on the page.
 *
 * Suggested layout: summary cards on top, main charts in the middle, comparison and alerts below.
 */

import { SensorComparisonChart } from '@/components/charts/SensorComparisonChart';
import { HumidityChart } from '@/components/charts/HumidityChart';
import { TemperatureChart } from '@/components/charts/TemperatureChart';
import { AlertBadge } from '@/components/widgets/AlertBadge';
import { LatestReadingCard } from '@/components/widgets/LatestReadingCard';
import type { Alert, SensorReading } from '@/types/sensor.types';

const placeholderReading: SensorReading = {
  id: 0,
  sensorId: '—',
  temperature: 0,
  humidity: 0,
  createdAt: new Date().toISOString(),
};

const placeholderAlert: Alert = {
  sensorId: '—',
  message: '—',
  severity: 'low',
  triggeredAt: new Date().toISOString(),
};

export default function DashboardPage() {
  const readings: SensorReading[] = [];

  return (
    <div className="flex flex-col gap-6 p-6">
      <section className="grid gap-4 md:grid-cols-2">
        <LatestReadingCard reading={placeholderReading} />
        <AlertBadge alert={placeholderAlert} />
      </section>
      <section className="grid gap-4 lg:grid-cols-2">
        <TemperatureChart data={readings} />
        <HumidityChart data={readings} />
      </section>
      <section>
        <SensorComparisonChart data={readings} />
      </section>
    </div>
  );
}
