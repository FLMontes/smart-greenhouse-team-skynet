'use client';

import { useState, useEffect } from 'react';
import type { SensorReading } from '@/types/sensor.types';
import { sensorService } from '@/services/sensors.service';

const formatOptionalValue = (value: number | undefined, unit: string): string => {
  return value === undefined ? 'N/A' : `${value.toFixed(1)}${unit}`;
};

const formatReadingDateTime = (reading: SensorReading): string => {
  const rawDate = reading.createdAt ?? reading.timestamp;

  if (!rawDate) {
    return 'Sin fecha';
  }

  const date = new Date(rawDate);

  if (Number.isNaN(date.getTime())) {
    return 'Fecha inválida';
  }

  return date.toLocaleString('es-AR', {
    dateStyle: 'short',
    timeStyle: 'short',
  });
};

export function HistoryTable() {
  const [data, setData] = useState<SensorReading[]>([]);
  const [page, setPage] = useState(1);
  const [total, setTotal] = useState(0);
  const [dateFilter, setDateFilter] = useState('');
  const [loading, setLoading] = useState(false);
  const limit = 10;

  useEffect(() => {
    let isMounted = true;

    const fetchData = async (showLoading = false) => {
      if (showLoading) {
        setLoading(true);
      }

      try {
        const result = await sensorService.getHistoricalReadings(page, limit, dateFilter);

        if (!isMounted) {
          return;
        }

        setData(result.data);
        setTotal(result.total);
      } catch (error) {
        console.error('Error fetching history:', error);
      } finally {
        if (isMounted && showLoading) {
          setLoading(false);
        }
      }
    };

    fetchData(true);

    const intervalId = window.setInterval(() => {
      fetchData(false);
    }, 2000);

    return () => {
      isMounted = false;
      window.clearInterval(intervalId);
    };
  }, [page, dateFilter]);

  const totalPages = Math.ceil(total / limit);

  return (
    <section className="rounded-lg border border-slate-700 bg-slate-800 p-6 shadow-md text-slate-100">
      <div className="mb-4 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h2 className="text-base font-semibold text-slate-200">
            Histórico de Telemetría Real
          </h2>
          <p className="text-sm text-slate-400">
            Registros históricos reales recibidos desde el backend.
          </p>
        </div>

        <div className="flex items-center gap-2">
          <span className="text-xs text-slate-400">Filtrar Fecha:</span>
          <input
            type="date"
            value={dateFilter}
            onChange={(e) => {
              setDateFilter(e.target.value);
              setPage(1);
            }}
            className="rounded border border-slate-600 bg-slate-700 px-2 py-1 text-xs text-slate-200 focus:outline-none focus:border-emerald-500"
          />
        </div>
      </div>

      <div className="overflow-x-auto">
        <table className="w-full min-w-[850px] border-collapse text-left text-sm">
          <thead>
            <tr className="border-b border-slate-700 text-xs uppercase text-slate-400">
              <th className="py-3 pr-4 font-medium">Fecha / Hora</th>
              <th className="py-3 pr-4 font-medium">Sensor</th>
              <th className="py-3 pr-4 font-medium">Temperatura</th>
              <th className="py-3 pr-4 font-medium">Humedad</th>
              <th className="py-3 pr-4 font-medium">Luz</th>
              <th className="py-3 pr-4 font-medium">CO₂</th>
            </tr>
          </thead>

          <tbody>
            {loading ? (
              <tr>
                <td
                  colSpan={6}
                  className="py-8 text-center text-slate-400 animate-pulse"
                >
                  Cargando telemetría...
                </td>
              </tr>
            ) : data.length === 0 ? (
              <tr>
                <td colSpan={6} className="py-8 text-center text-slate-400">
                  No hay registros para esta fecha.
                </td>
              </tr>
            ) : (
              data.map((reading) => (
                <tr
                  key={`${reading.id}-${reading.createdAt ?? reading.timestamp ?? 'no-date'}`}
                  className="border-b border-slate-700/50 text-slate-200 hover:bg-slate-700/30"
                >
                  <td className="py-3 pr-4 text-slate-400">
                    {formatReadingDateTime(reading)}
                  </td>
                  <td className="py-3 pr-4">{reading.sensorId}</td>
                  <td className="py-3 pr-4">
                    {formatOptionalValue(reading.temperature, '°C')}
                  </td>
                  <td className="py-3 pr-4">
                    {formatOptionalValue(reading.humidity, '%')}
                  </td>
                  <td className="py-3 pr-4">
                    {formatOptionalValue(reading.light, ' lx')}
                  </td>
                  <td className="py-3 pr-4">
                    {formatOptionalValue(reading.co2, ' ppm')}
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>

      <div className="mt-4 flex items-center justify-between pt-4 border-t border-slate-700 text-xs text-slate-400">
        <button
          onClick={() => setPage((p) => Math.max(1, p - 1))}
          disabled={page === 1 || loading}
          className="rounded border border-slate-600 bg-slate-700 px-3 py-1.5 hover:bg-slate-600 disabled:opacity-40"
        >
          Anterior
        </button>

        <span>
          Página {page} de {totalPages || 1} ({total} registros)
        </span>

        <button
          onClick={() => setPage((p) => p + 1)}
          disabled={page >= totalPages || loading}
          className="rounded border border-slate-600 bg-slate-700 px-3 py-1.5 hover:bg-slate-600 disabled:opacity-40"
        >
          Siguiente
        </button>
      </div>
    </section>
  );
}