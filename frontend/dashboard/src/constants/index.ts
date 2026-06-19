// Application constants
export const POLLING_INTERVALS = {
  SENSORS: 5000, // 5 seconds
  ALERTS: 3000, // 3 seconds
  ANALYTICS: 30000, // 30 seconds
};

export const API_ENDPOINTS = {
  SENSORS: '/api/sensors',
  ALERTS: '/api/alerts',
  DASHBOARD: '/api/dashboard',
};

// Greenhouse theme colors
export const GREENHOUSE_COLORS = {
  primary: '#10b981', // Emerald green - healthy plants
  primaryDark: '#059669',
  critical: '#ef4444', // Red - alert
  warning: '#fbbf24', // Amber - warning
  info: '#3b82f6', // Blue - humidity/water
  success: '#10b981', // Green - optimal
  neutral: '#6b7280', // Gray
};

export const CHART_COLORS = {
  temperature: '#ef4444', // Red
  humidity: '#3b82f6', // Blue
  light: '#fbbf24', // Yellow/Amber
  pressure: '#10b981', // Green
};

export const SENSOR_STATUS_COLORS = {
  optimal: GREENHOUSE_COLORS.success,
  warning: GREENHOUSE_COLORS.warning,
  critical: GREENHOUSE_COLORS.critical,
};

// Default placeholder data
export const PLACEHOLDER_READINGS = {
  temperature: 22,
  humidity: 65,
  light: 15000,
};
