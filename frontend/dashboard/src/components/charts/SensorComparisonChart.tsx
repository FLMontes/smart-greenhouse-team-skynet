'use client';

import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import type { SensorComparisonChartProps } from '@/types/sensor.types';

const COLORS = ['#ef4444', '#3b82f6', '#10b981', '#f59e0b', '#8b5cf6'];

export function SensorComparisonChart(props: SensorComparisonChartProps) {
  const { data } = props;
  const chartData = data.length > 0 ? data : [
    { time: '00:00', 'Sensor A': 20, 'Sensor B': 22, 'Sensor C': 19 },
    { time: '04:00', 'Sensor A': 18, 'Sensor B': 20, 'Sensor C': 21 },
    { time: '08:00', 'Sensor A': 22, 'Sensor B': 24, 'Sensor C': 20 },
    { time: '12:00', 'Sensor A': 25, 'Sensor B': 26, 'Sensor C': 23 },
    { time: '16:00', 'Sensor A': 28, 'Sensor B': 29, 'Sensor C': 26 },
    { time: '20:00', 'Sensor A': 24, 'Sensor B': 25, 'Sensor C': 22 },
  ];

  return (
    <div className="rounded-lg border border-gray-200 bg-white p-6 shadow-sm">
      <h3 className="text-lg font-semibold text-gray-800">Comparación de Sensores</h3>
      <ResponsiveContainer width="100%" height={300}>
        <LineChart data={chartData}>
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="time" />
          <YAxis />
          <Tooltip />
          <Legend />
          <Line type="monotone" dataKey="Sensor A" stroke={COLORS[0]} strokeWidth={2} />
          <Line type="monotone" dataKey="Sensor B" stroke={COLORS[1]} strokeWidth={2} />
          <Line type="monotone" dataKey="Sensor C" stroke={COLORS[2]} strokeWidth={2} />
        </LineChart>
      </ResponsiveContainer>
    </div>
  );
}
