'use client';

/**
 * Compares multiple sensors on one chart (Recharts).
 *
 * - Must have `'use client'`.
 * - Receives `data: SensorReading[]` that may include multiple `sensorId` values.
 * - Hint for students: group by `sensorId`, render one `Line` per sensor with distinct colors.
 * - Must not call `fetch()` or contain data-fetching logic.
 */

import type { SensorComparisonChartProps } from '@/types/sensor.types';

export function SensorComparisonChart(props: SensorComparisonChartProps) {
  void props;
  return null;
}
