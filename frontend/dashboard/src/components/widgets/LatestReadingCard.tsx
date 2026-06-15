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
  const { reading } = props;
  return (
    <div className="rounded-lg border border-gray-200 bg-white p-6 shadow-sm">
      <h3 className="text-sm font-medium text-gray-600">Última Lectura</h3>
      <div className="mt-4 space-y-3">
        <div className="flex justify-between">
          <span className="text-sm text-gray-500">Sensor:</span>
          <span className="font-semibold">{reading.sensorId}</span>
        </div>
        <div className="flex justify-between">
          <span className="text-sm text-gray-500">Temperatura:</span>
          <span className="font-semibold text-red-600">{reading.temperature}°C</span>
        </div>
        <div className="flex justify-between">
          <span className="text-sm text-gray-500">Humedad:</span>
          <span className="font-semibold text-blue-600">{reading.humidity}%</span>
        </div>
        <div className="flex justify-between border-t border-gray-100 pt-3">
          <span className="text-xs text-gray-400">Hora:</span>
          <span className="text-xs text-gray-500">
            {new Date(reading.createdAt).toLocaleTimeString()}
          </span>
        </div>
      </div>
    </div>
  );
}
