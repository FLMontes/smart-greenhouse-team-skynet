'use client';

import { Sun } from 'lucide-react';
import type { CurrentLightCardProps } from '@/types/sensor.types';
import { GREENHOUSE_COLORS } from '@/constants';

export function CurrentLightCard({
  light,
  sensorId,
  timestamp,
}: CurrentLightCardProps) {
  const formatTime = (iso: string) => {
    return new Date(iso).toLocaleTimeString('es-AR', {
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  return (
    <div className="rounded-lg border-2 border-amber-200 bg-gradient-to-br from-amber-50 to-white p-6 shadow-sm hover:shadow-md transition-shadow">
      <div className="flex items-start justify-between mb-4">
        <div className="flex items-center gap-3">
          <div
            className="rounded-full p-3"
            style={{ backgroundColor: `${GREENHOUSE_COLORS.warning}20` }}
          >
            <Sun size={24} style={{ color: GREENHOUSE_COLORS.warning }} />
          </div>
          <div>
            <h3 className="text-sm font-medium text-gray-600">Luz</h3>
            <p className="text-xs text-gray-400">{sensorId}</p>
          </div>
        </div>
        <div className="w-3 h-3 rounded-full bg-amber-500"></div>
      </div>

      <div className="mb-4">
        <div className="flex items-baseline gap-2">
          <span className="text-5xl font-bold text-amber-700">
            {light.toFixed(1)}
          </span>
          <span className="text-lg font-semibold text-gray-600">lux</span>
        </div>
      </div>

      <div className="border-t border-amber-100 pt-3">
        <p className="text-xs text-gray-500">
          Última actualización: {formatTime(timestamp)}
        </p>
      </div>
    </div>
  );
}
