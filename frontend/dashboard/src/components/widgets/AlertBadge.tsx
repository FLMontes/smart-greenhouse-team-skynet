'use client';

/**
 * Severity-styled alert badge.
 *
 * - Must have `'use client'`.
 * - Receives `alert: Alert` via props.
 * - Badge color maps to `severity`: `low` → yellow, `medium` → orange, `high` → red (Tailwind classes).
 */

import type { AlertBadgeProps } from '@/types/sensor.types';

export function AlertBadge(props: AlertBadgeProps) {
  void props;
  return null;
}
