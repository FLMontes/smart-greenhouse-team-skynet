'use client';

import {
  ResponsiveContainer,
  AreaChart,
  Area,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
} from 'recharts';
import type { CO2ChartProps } from '@/types/sensor.types';

export function CO2Chart({ data }: CO2ChartProps) {
  return (
    <div className="rounded-xl border border-gray-100 bg-white p-6 shadow-sm">
      <div className="mb-6">
        <h3 className="text-lg font-bold text-gray-800">Dióxido de Carbono</h3>
        <p className="text-sm text-gray-400">Registro histórico de CO₂ (ppm)</p>
      </div>

      <div className="h-[300px] w-full">
        <ResponsiveContainer width="100%" height="100%">
          <AreaChart
            data={data}
            margin={{ top: 10, right: 10, left: -20, bottom: 0 }}
          >
            <defs>
              <linearGradient id="colorCo2" x1="0" y1="0" x2="0" y2="1">
                <stop offset="5%" stopColor="#a855f7" stopOpacity={0.2} />
                <stop offset="95%" stopColor="#a855f7" stopOpacity={0} />
              </linearGradient>
            </defs>

            <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f0f0f0" />

            <XAxis
              dataKey="createdAt"
              stroke="#9ca3af"
              fontSize={12}
              tickLine={false}
              axisLine={false}
              tickFormatter={(value) => {
                const date = new Date(value);
                return Number.isNaN(date.getTime())
                  ? String(value)
                  : date.toLocaleTimeString('es-AR', {
                      hour: '2-digit',
                      minute: '2-digit',
                    });
              }}
            />

            <YAxis
              stroke="#9ca3af"
              fontSize={12}
              tickLine={false}
              axisLine={false}
              domain={['dataMin - 50', 'dataMax + 50']}
            />

            <Tooltip
              contentStyle={{
                background: '#fff',
                border: '1px solid #e5e7eb',
                borderRadius: '8px',
              }}
              labelFormatter={(label) => {
                const date = new Date(String(label));
                return Number.isNaN(date.getTime())
                  ? `Hora: ${label}`
                  : `Hora: ${date.toLocaleTimeString('es-AR', {
                      hour: '2-digit',
                      minute: '2-digit',
                    })}`;
              }}
              formatter={(value) => [`${Number(value).toFixed(1)} ppm`, 'CO₂']}
            />

            <Area
              type="monotone"
              dataKey="co2"
              stroke="#a855f7"
              strokeWidth={2}
              fillOpacity={1}
              fill="url(#colorCo2)"
            />
          </AreaChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
}