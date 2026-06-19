'use client';

import { Wind } from 'lucide-react';
import type { CurrentCO2CardProps } from '@/types/sensor.types';

const CO2_COLOR = '#a855f7';

export function CurrentCO2Card({ co2, sensorId, timestamp }: CurrentCO2CardProps) {
  const formatTime = (iso: string) => {
    const date = new Date(iso);

    if (Number.isNaN(date.getTime())) {
      return 'Sin fecha';
    }

    return date.toLocaleTimeString('es-AR', {
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  return (
    <div className="rounded-lg border-2 border-purple-200 bg-gradient-to-br from-purple-50 to-white p-6 shadow-sm hover:shadow-md transition-shadow">
      <div className="flex items-start justify-between mb-4">
        <div className="flex items-center gap-3">
          <div className="rounded-full p-3" style={{ backgroundColor: `${CO2_COLOR}20` }}>
            <Wind size={24} style={{ color: CO2_COLOR }} />
          </div>

          <div>
            <h3 className="text-sm font-medium text-gray-600">CO₂</h3>
            <p className="text-xs text-gray-400">{sensorId}</p>
          </div>
        </div>

        <div className="w-3 h-3 rounded-full bg-purple-500"></div>
      </div>

      <div className="mb-4">
        <div className="flex items-baseline gap-2">
          <span className="text-5xl font-bold text-purple-700">
            {co2.toFixed(1)}
          </span>
          <span className="text-lg font-semibold text-gray-600">ppm</span>
        </div>
      </div>

      <div className="border-t border-purple-100 pt-3">
        <p className="text-xs text-gray-500">
          Última actualización: {formatTime(timestamp)}
        </p>
      </div>
    </div>
  );
}