'use client';

import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import type { HumidityChartProps } from '@/types/sensor.types';

export function HumidityChart(props: HumidityChartProps) {
  const { data } = props;

  // Transform data for chart display
  const chartData = data.length > 0
    ? data.map(reading => ({
        time: new Date(reading.createdAt).toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' }),
        humidity: reading.humidity,
      }))
    : [
        { time: '00:00', humidity: 55 },
        { time: '04:00', humidity: 60 },
        { time: '08:00', humidity: 65 },
        { time: '12:00', humidity: 60 },
        { time: '16:00', humidity: 70 },
        { time: '20:00', humidity: 75 },
      ];

  return (
    <div className="rounded-lg border border-gray-200 bg-white p-6 shadow-sm">
      <h3 className="text-lg font-semibold text-gray-800">Humedad</h3>
      <p className="text-sm text-gray-500 mb-4">Registro histórico de humedad (%)</p>
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="time" />
          <YAxis />
          <Tooltip formatter={(value) => `${(value as number).toFixed(1)}%`} />
          <Line type="monotone" dataKey="humidity" stroke="#3b82f6" strokeWidth={2} dot={false} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}
