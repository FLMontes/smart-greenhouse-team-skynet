'use client';

/**
 * Client hook that polls the sensors service on an interval.
 *
 * - Must have `'use client'` at the top of this file.
 * - Accepts `intervalMs` (default 5000ms) to control poll frequency.
 * - Uses `useState` for `data`, `loading`, and `error`.
 * - Uses `useEffect` with `setInterval` to call the service repeatedly.
 * - Must clear the interval on unmount.
 * - Returns `{ data, loading, error }` for consumers.
 *
 * Students implement the hook body; polling logic must not live inside presentational components.
 */

export {};
