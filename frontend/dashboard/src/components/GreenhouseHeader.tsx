'use client';

import { Leaf, Circle } from 'lucide-react';
import { GREENHOUSE_COLORS } from '@/constants';

interface GreenhouseHeaderProps {
  isOnline?: boolean;
  lastUpdate?: string;
}

export function GreenhouseHeader({
  isOnline = true,
  lastUpdate = new Date().toISOString(),
}: GreenhouseHeaderProps) {
  const formatLastUpdate = (iso: string) => {
    const now = new Date();
    const time = new Date(iso);
    const diffMs = now.getTime() - time.getTime();
    const diffSec = Math.floor(diffMs / 1000);

    if (diffSec < 60) return 'hace unos segundos';
    if (diffSec < 3600) return `hace ${Math.floor(diffSec / 60)} min`;
    return `hace ${Math.floor(diffSec / 3600)}h`;
  };

  return (
    <div className="mb-6 rounded-lg border border-emerald-200 bg-gradient-to-r from-emerald-50 to-green-50 p-6">
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-4">
          <div className="rounded-full p-3 bg-emerald-100">
            <Leaf size={32} color={GREENHOUSE_COLORS.primary} />
          </div>
          <div>
            <h1 className="text-3xl font-bold text-emerald-900">
              🌱 Invernadero Inteligente
            </h1>
            <p className="text-sm text-emerald-600 mt-1">
              Sistema de monitoreo de sensores en tiempo real
            </p>
          </div>
        </div>

        <div className="flex items-center gap-6 text-right">
          <div>
            <p className="text-xs text-gray-500 uppercase tracking-wider">
              Última actualización
            </p>
            <p className="text-sm font-semibold text-gray-700">
              {formatLastUpdate(lastUpdate)}
            </p>
          </div>

          <div className="flex items-center gap-2">
            <Circle
              size={12}
              fill={isOnline ? GREENHOUSE_COLORS.success : GREENHOUSE_COLORS.critical}
              color={isOnline ? GREENHOUSE_COLORS.success : GREENHOUSE_COLORS.critical}
            />
            <span
              className="text-sm font-semibold"
              style={{
                color: isOnline ? GREENHOUSE_COLORS.success : GREENHOUSE_COLORS.critical,
              }}
            >
              {isOnline ? 'En línea' : 'Desconectado'}
            </span>
          </div>
        </div>
      </div>
    </div>
  );
}
