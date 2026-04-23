# Docker Setup Guide

This guide will help you set up and run the PostgreSQL database for the Software Engineering Final Project, and optionally build a production Docker image for the Next.js dashboard.

## Prerequisites

Before starting, make sure you have installed:

- **Docker Desktop** (for macOS/Windows) or **Docker Engine** (for Linux)
  - Download from: https://www.docker.com/products/docker-desktop/
- **Docker Compose** (usually included with Docker Desktop)

### Verify Installation

Open a terminal and run:

```bash
docker --version
docker-compose --version
```

You should see version numbers if everything is installed correctly.

## Quick Start

### 1. Configure Environment Variables (First Time Only)

The project uses a centralized `.env` file at the root directory. Configure it before starting any services:

```bash
# From the project root
cd /path/to/soft-eng-2026-final-project
cp .env.example .env
```

The `.env` file contains configuration for:
- PostgreSQL database (credentials, ports, database name)
- All backend services (Flask, Spring Boot, NestJS)
- Frontend configuration

**Note:** The `.env` file is already configured with development defaults. For production, update these values with secure credentials.

### 2. Navigate to the Docker Directory

```bash
cd docker
```

### 3. Start the PostgreSQL Database

Run the following command to start the database:

```bash
docker-compose up -d
```

**What this does:**
- `-d` flag runs containers in detached mode (in the background)
- Downloads the PostgreSQL image if not already present
- Creates and starts the database container
- Sets up persistent storage for your data
- Loads environment variables from the root `.env` file

### 4. Verify the Database is Running

Check that the container is running:

```bash
docker-compose ps
```

You should see the `soft-eng-postgres` container with status "Up".

### 5. Check Database Health

Wait a few seconds and verify the database is healthy:

```bash
docker-compose logs postgres
```

Look for a message like: `database system is ready to accept connections`

## Database Connection Details

The credentials are defined in the root `.env` file. With the default configuration:

```
Host: localhost
Port: 5433
Database: soft_eng_db
Username: soft_eng_user
Password: dev_password_2026
```

The container still listens on **5432 inside Docker**; **5433** is the port on your machine (`5433:5432` mapping). Keep **`POSTGRES_PORT`** in the root `.env` aligned with the left-hand side of `ports:` in `docker-compose.yml`.

**Note:** These values can be changed by editing the `.env` file at the project root before starting the containers.

### Connection String Examples

**PostgreSQL URL format:**
```
postgresql://soft_eng_user:dev_password_2026@localhost:5433/soft_eng_db
```

**JDBC URL (for Spring Boot):**
```
jdbc:postgresql://localhost:5433/soft_eng_db
```

## Common Commands

### Stop the Database

```bash
docker-compose down
```

This stops and removes the containers, but **keeps your data safe** in the volume.

### Stop and Remove All Data

⚠️ **Warning**: This will delete all database data!

```bash
docker-compose down -v
```

### Restart the Database

```bash
docker-compose restart
```

### View Database Logs

```bash
docker-compose logs -f postgres
```

Press `Ctrl+C` to stop following logs.

### Access PostgreSQL CLI

To run SQL commands directly:

```bash
docker-compose exec postgres psql -U soft_eng_user -d soft_eng_db
```

Example commands once inside:
```sql
\l                  -- List all databases
\dt                 -- List all tables
\q                  -- Quit
SELECT version();   -- Check PostgreSQL version
```

## Optional: pgAdmin (Database GUI)

pgAdmin is a graphical tool to manage your PostgreSQL database.

### Enable pgAdmin

1. Open `docker-compose.yml`
2. Uncomment the pgAdmin section (lines starting with `#`)
3. Restart the containers:

```bash
docker-compose down
docker-compose up -d
```

### Access pgAdmin

- Open your browser and go to: http://localhost:5050
- Login with:
  - Email: `admin@softeng.com`
  - Password: `admin`

### Add Server in pgAdmin

1. Click "Add New Server"
2. In the "General" tab:
   - Name: `Soft Eng DB`
3. In the "Connection" tab:
   - Host: `postgres` (use the service name, not localhost)
   - Port: `5432`
   - Database: `soft_eng_db`
   - Username: `soft_eng_user`
   - Password: `dev_password_2026`
4. Click "Save"

## Troubleshooting

### Database port already in use on the host

The compose file publishes Postgres on **host port 5433** by default so it does not fight with a typical local install on **5432**.

If **5433** is also taken, pick another free host port in `docker-compose.yml`:

```yaml
ports:
  - "5434:5432"
```

Then set **`POSTGRES_PORT=5434`** (and matching URLs) in the root `.env`.

### Container Won't Start

Check the logs for errors:
```bash
docker-compose logs postgres
```

### Data Not Persisting

Make sure you're not using the `-v` flag when stopping:
```bash
docker-compose down    # Good - keeps data
docker-compose down -v # Bad - deletes data
```

### Permission Issues (Linux)

If you get permission errors:
```bash
sudo docker-compose up -d
```

Or add your user to the docker group:
```bash
sudo usermod -aG docker $USER
```

Then log out and log back in.

## Data Persistence

Your database data is stored in a Docker volume named `postgres_data`. This means:

- ✅ Data survives container restarts
- ✅ Data survives `docker-compose down`
- ❌ Data is deleted with `docker-compose down -v`

### View Volumes

```bash
docker volume ls
```

### Backup Your Data

```bash
docker-compose exec postgres pg_dump -U soft_eng_user soft_eng_db > backup.sql
```

### Restore from Backup

```bash
docker-compose exec -T postgres psql -U soft_eng_user -d soft_eng_db < backup.sql
```

## Backend Configuration

After starting the database, update your backend configuration files:

### Spring Boot (`application.yaml`)

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/soft_eng_db
    username: soft_eng_user
    password: dev_password_2026
```

### Flask (root `.env`)

The Flask app loads the **monorepo root** `.env` and builds `SQLALCHEMY_DATABASE_URI` from `POSTGRES_*` (or uses `DATABASE_URL` if set to a real URL). Defaults match Docker PostgreSQL:

```env
POSTGRES_HOST=localhost
POSTGRES_PORT=5433
POSTGRES_DB=soft_eng_db
POSTGRES_USER=soft_eng_user
POSTGRES_PASSWORD=dev_password_2026
FLASK_APP=main:app
```

Run from `backend/flask/final_project` (see `backend/flask/final_project/readme.md`).

### NestJS (`app.module.ts` or `.env`)

```typescript
TypeOrmModule.forRoot({
  type: 'postgres',
  host: 'localhost',
  port: 5433,
  username: 'soft_eng_user',
  password: 'dev_password_2026',
  database: 'soft_eng_db',
})
```

## Next.js dashboard (optional Docker image)

You can build and run the IoT dashboard as a production container using `docker/nextjs.Dockerfile`. The image uses Next.js [standalone output](https://nextjs.org/docs/app/api-reference/config/next-config-js/output), which bundles a minimal Node server (`server.js`) suitable for deployment.

### Prerequisites

- Same Docker installation as above.
- The dashboard source lives in `frontend/dashboard` (see the root [README](../README.md)).

### Important: `NEXT_PUBLIC_API_URL`

The dashboard uses the same variable as local dev: set it in the **monorepo root** `.env` (see root `.env.example`). For the Docker image, Next.js **inlines** `NEXT_PUBLIC_*` at **build** time, so pass the same value as a **build argument** when building—`docker run` alone cannot change what the browser bundle contains.

Use a URL that the **user’s browser** can reach (for example `http://localhost:3000` if the API is published on the host). It is usually **not** the internal Docker service name unless you also expose the API under that name on the host.

### Build the image

From the **repository root** (not inside `docker/`):

```bash
docker build -f docker/nextjs.Dockerfile \
  --build-arg NEXT_PUBLIC_API_URL=http://localhost:3000 \
  -t soft-eng-dashboard:latest \
  .
```

Keep `NEXT_PUBLIC_API_URL` aligned with the root `.env` and the backend you run (NestJS often `3000`, Spring `8080`, Flask `5000`).

### Run the container

```bash
docker run --rm -p 3001:3000 soft-eng-dashboard:latest
```

Then open **http://localhost:3001** in a browser. The app listens on port `3000` inside the container; the example maps host `3001` → container `3000` so you can run another service on host port `3000` at the same time.

### Notes for coursework

- Rebuild the image after changing `NEXT_PUBLIC_API_URL` or other public env vars.
- A root `.dockerignore` excludes `node_modules`, `.next`, and common build artifacts so the build context stays smaller and faster.
- The Dockerfile targets **Node 22** (aligned with `frontend/dashboard/.nvmrc`). Use `--build-arg NODE_VERSION=20` if you need another supported major.

## Need Help?

- **Docker Documentation**: https://docs.docker.com/
- **PostgreSQL Documentation**: https://www.postgresql.org/docs/
- **Docker Compose Reference**: https://docs.docker.com/compose/

---

**Enjoy VTupla! 🚀**
