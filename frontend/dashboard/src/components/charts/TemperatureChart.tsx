'use client';

import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import type { TemperatureChartProps } from '@/types/sensor.types';

export function TemperatureChart(props: TemperatureChartProps) {
  const { data } = props;
  const chartData = data.length > 0 ? data : [
    { time: '00:00', temperature: 0 },
    { time: '04:00', temperature: 5 },
    { time: '08:00', temperature: 10 },
    { time: '12:00', temperature: 15 },
    { time: '16:00', temperature: 20 },
    { time: '20:00', temperature: 18 },
  ];

  return (
    <div className="rounded-lg border border-gray-200 bg-white p-6 shadow-sm">
      <h3 className="text-lg font-semibold text-gray-800">Temperatura</h3>
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="time" />
          <YAxis />
          <Tooltip />
          <Line type="monotone" dataKey="temperature" stroke="#ef4444" strokeWidth={2} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}
