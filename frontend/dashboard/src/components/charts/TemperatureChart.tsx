'use client';

/**
 * Temperature over time chart (Recharts).
 *
 * - Must have `'use client'` because Recharts uses client-only APIs.
 * - Receives `data: SensorReading[]` via props.
 * - Uses a Recharts `LineChart` to plot temperature vs. time (students implement).
 * - Must not call `fetch()` or contain data-fetching logic — parent passes data only.
 */

import type { TemperatureChartProps } from '@/types/sensor.types';

export function TemperatureChart(props: TemperatureChartProps) {
  void props;
  return null;
}
