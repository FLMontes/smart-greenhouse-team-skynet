import type { AlertBadgeProps } from '@/types/sensor.types';

const severityColors = {
  low: 'bg-yellow-50 text-yellow-800 border-yellow-200',
  medium: 'bg-orange-50 text-orange-800 border-orange-200',
  high: 'bg-red-50 text-red-800 border-red-200',
};

export function AlertBadge(props: AlertBadgeProps) {
  const { alert } = props;
  const severityColor = severityColors[alert.severity];

  return (
    <div className={`rounded-lg border p-6 ${severityColor}`}>
      <h3 className="text-sm font-medium">Alerta</h3>
      <div className="mt-4 space-y-2">
        <div>
          <p className="text-xs opacity-75">Sensor ID</p>
          <p className="font-semibold">{alert.sensorId}</p>
        </div>
        <div>
          <p className="text-xs opacity-75">Mensaje</p>
          <p className="font-semibold">{alert.message}</p>
        </div>
        <div>
          <p className="text-xs opacity-75">Severidad</p>
          <span className="inline-block rounded px-2 py-1 text-xs font-bold capitalize">
            {alert.severity}
          </span>
        </div>
      </div>
    </div>
  );
}
