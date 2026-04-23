'use client';

/**
 * Humidity over time chart (Recharts).
 *
 * - Must have `'use client'`.
 * - Receives `data: SensorReading[]` via props.
 * - Uses a Recharts `LineChart` or `BarChart` for humidity trends (students implement).
 * - Must not call `fetch()` or contain data-fetching logic.
 */

import type { HumidityChartProps } from '@/types/sensor.types';

export function HumidityChart(props: HumidityChartProps) {
  void props;
  return null;
}
