'use client';

import { Thermometer } from 'lucide-react';
import type { CurrentTemperatureCardProps } from '@/types/sensor.types';
import { GREENHOUSE_COLORS } from '@/constants';

export function CurrentTemperatureCard({
  temperature,
  sensorId,
  timestamp,
}: CurrentTemperatureCardProps) {
  const formatTime = (iso: string) => {
    return new Date(iso).toLocaleTimeString('es-AR', {
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  return (
    <div className="rounded-lg border-2 border-emerald-200 bg-gradient-to-br from-emerald-50 to-white p-6 shadow-sm hover:shadow-md transition-shadow">
      <div className="flex items-start justify-between mb-4">
        <div className="flex items-center gap-3">
          <div
            className="rounded-full p-3"
            style={{ backgroundColor: `${GREENHOUSE_COLORS.primary}20` }}
          >
            <Thermometer
              size={24}
              style={{ color: GREENHOUSE_COLORS.primary }}
            />
          </div>
          <div>
            <h3 className="text-sm font-medium text-gray-600">Temperatura</h3>
            <p className="text-xs text-gray-400">{sensorId}</p>
          </div>
        </div>
        <div className="w-3 h-3 rounded-full bg-emerald-500"></div>
      </div>

      <div className="mb-4">
        <div className="flex items-baseline gap-2">
          <span className="text-5xl font-bold text-emerald-700">
            {temperature.toFixed(1)}
          </span>
          <span className="text-lg font-semibold text-gray-600">°C</span>
        </div>
      </div>

      <div className="border-t border-emerald-100 pt-3">
        <p className="text-xs text-gray-500">
          Última actualización: {formatTime(timestamp)}
        </p>
      </div>
    </div>
  );
}
