import path from "node:path";
import { loadEnvConfig } from "@next/env";
import type { NextConfig } from "next";

// Single source of truth: monorepo root `.env` / `.env.local` (see root `.env.example`).
// Next still loads `frontend/dashboard/.env*` afterward if students add overrides there.
const monorepoRoot = path.resolve(__dirname, "../..");
loadEnvConfig(monorepoRoot);

const nextConfig: NextConfig = {
  // Required for the production image in `docker/nextjs.Dockerfile` (standalone server bundle).
  output: "standalone",
};

export default nextConfig;
