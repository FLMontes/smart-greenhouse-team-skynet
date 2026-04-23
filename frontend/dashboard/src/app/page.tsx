import { redirect } from 'next/navigation';

/**
 * Root page — immediately sends users to the dashboard.
 *
 * No further changes expected here; keeps a single entry point for the app.
 * Uses `redirect('/dashboard')` from `next/navigation`.
 */

export default function Home() {
  redirect('/dashboard');
}
