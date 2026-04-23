'use client';

/**
 * Card showing a single sensor reading.
 *
 * - Must have `'use client'` (interactive styling / future actions).
 * - Receives `reading: SensorReading` via props.
 * - Displays `sensorId`, `temperature`, `humidity`, and `createdAt` with Tailwind utilities (students implement).
 * - Must not call `fetch()` or contain data-fetching logic.
 */

import type { LatestReadingCardProps } from '@/types/sensor.types';

export function LatestReadingCard(props: LatestReadingCardProps) {
  void props;
  return null;
}
