'use client';

import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import type { LightChartProps } from '@/types/sensor.types';
import { CHART_COLORS } from '@/constants';

export function LightChart({ data }: LightChartProps) {
  // Transform data for chart display
  const chartData = data.length > 0
    ? data.map(reading => ({
        time: new Date(reading.createdAt).toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' }),
        light: reading.light,
      }))
    : [
        { time: '00:00', light: 0 },
        { time: '04:00', light: 0 },
        { time: '08:00', light: 8000 },
        { time: '12:00', light: 25000 },
        { time: '16:00', light: 20000 },
        { time: '20:00', light: 0 },
      ];

  return (
    <div className="rounded-lg border border-gray-200 bg-white p-6 shadow-sm">
      <h3 className="text-lg font-semibold text-gray-800">Iluminación</h3>
      <p className="text-sm text-gray-500 mb-4">Registro histórico de luz (lux)</p>
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="time" />
          <YAxis />
          <Tooltip
            formatter={(value) => `${(value as number).toFixed(0)} lux`}
            labelFormatter={(label) => `Hora: ${label}`}
          />
          <Line
            type="monotone"
            dataKey="light"
            stroke={CHART_COLORS.light}
            strokeWidth={2}
            dot={false}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}
