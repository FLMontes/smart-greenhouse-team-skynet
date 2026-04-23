<a id="about-top"></a>

<!-- PROJECT SHIELDS -->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<!-- PROJECT LOGO -->
<br />
<div align="center">
  <h1 align="center"> Software Engineering 2026 - Final Project</h1>

  <p align="center">
    Complete IoT System with ESP32, Multi-Backend Architecture & Real-time Dashboard
    <br />
    <strong>Universidad Nacional de Córdoba (UNC)</strong>
    <br />
    <br />
    <a href="https://github.com/juanma-gil/soft-eng-2026-final-project"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/juanma-gil/soft-eng-2026-final-project/issues/new?labels=bug">Report Bug</a>
    ·
    <a href="https://github.com/juanma-gil/soft-eng-2026-final-project/issues/new?labels=enhancement">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>📑 Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li><a href="#installation">Installation</a></li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

This repository serves as the foundation for the Software Engineering final project at UNC. Students fork this repo to develop a **complete end-to-end IoT system** that includes:

-  **Data Acquisition** with ESP32 microcontrollers and sensors
-  **Multi-Backend Architecture** supporting Flask, Spring Boot, and NestJS
-  **Real-time Data Visualization** with interactive dashboards and analytics
-  **PostgreSQL Database** for reliable data persistence
-  **Docker Compose** setup for easy local development

**Course delivery:** the template includes **multiple sample backends** (Flask, Spring Boot, NestJS). Each team must ship **exactly one** backend for the graded project; treat the others as reference or disable them in your fork. Requirements and process are in **[docs/CONSIGNA.md](docs/CONSIGNA.md)**.

### Built With

[![Spring Boot][Spring-badge]][Spring-url]
[![Flask][Flask-badge]][Flask-url]
[![NestJS][NestJS-badge]][NestJS-url]
[![Next.js][Next-badge]][Next-url]
[![PostgreSQL][PostgreSQL-badge]][PostgreSQL-url]
[![Docker][Docker-badge]][Docker-url]
[![ESP32][ESP32-badge]][ESP32-url]
[![PlatformIO][PlatformIO-badge]][PlatformIO-url]

<p align="right">(<a href="#about-top">back to top</a>)</p>

<!-- INSTALLATION -->
## Installation

### Prerequisites

Make sure you have the following installed:
- **Docker Desktop** or Docker Engine
- **Git**
- Your preferred backend runtime (Java 17+, Python 3.9+, or Node.js 18+)
- **PlatformIO** (for ESP32 firmware development)

### Setup

1. **Fork and clone the repository**
   ```bash
   git clone https://github.com/your-username/soft-eng-2026-final-project.git
   cd soft-eng-2026-final-project
   ```

2. **Configure environment variables**
   ```bash
   cp .env.example .env
   ```
   > The `.env` file contains all configuration for database, backends, and services.

3. **Start the database**
   ```bash
   cd docker
   docker-compose up -d
   ```

4. **Verify installation**
   ```bash
   docker-compose ps
   ```

<p align="right">(<a href="#about-top">back to top</a>)</p>

<!-- USAGE -->
## Usage

### Running Backend Services

Choose your preferred backend framework:

**Flask:**
```bash
cd backend/flask/final_project
pip install -r requirements.txt
export FLASK_APP=main:app
flask db upgrade   # first time, with PostgreSQL up and root .env configured
python main.py
```

Uses the **repository root** `.env` (see `backend/flask/final_project/readme.md`).

**Spring Boot:**
```bash
cd backend/spring/final-project
./gradlew bootRun
```

Requires **JDK 21** and the **repository root** `.env` (same `POSTGRES_*` / `SPRING_PORT` as in `.env.example`). PostgreSQL should be up (e.g. `docker compose` in `docker/`). Swagger UI: http://localhost:8080/swagger-ui.html

**NestJS:**
```bash
cd backend/nestjs
npm install
npm run start:dev
```

### ESP32 Firmware

```bash
cd firmware/esp32
pio run -t upload
```

### Centralized Configuration

This project uses a **single `.env` file** at the root to avoid configuration inconsistencies:
- ✅ Single source of truth for all services
- ✅ No duplicate credentials across backends
- ✅ Easy to manage and secure

For more detailed documentation, visit the [docs](docs/) folder.

<p align="right">(<a href="#about-top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- [x] PostgreSQL Docker setup with health checks
- [x] Centralized environment configuration
- [x] Multi-backend support (Flask, Spring Boot, NestJS)
- [ ] ESP32 sensor integration (temperature, humidity, etc.)
- [ ] Real-time data ingestion API
- [ ] Frontend dashboard with charts
- [ ] WebSocket support for live updates
- [ ] Authentication & authorization
- [ ] API documentation (Swagger/OpenAPI)
- [ ] CI/CD pipeline
- [ ] Production deployment guide

See the [open issues](https://github.com/juanma-gil/soft-eng-2026-final-project/issues) for a full list of proposed features and known issues.

<p align="right">(<a href="#about-top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Top contributors:

<a href="https://github.com/juanma-gil/soft-eng-2026-final-project/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=juanma-gil/soft-eng-2026-final-project" alt="contrib.rocks image" />
</a>

<p align="right">(<a href="#about-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

**Universidad Nacional de Córdoba**  
Software Engineering Course - 2026

Project Link: [https://github.com/juanma-gil/soft-eng-2026-final-project](https://github.com/juanma-gil/soft-eng-2026-final-project)

<p align="right">(<a href="#about-top">back to top</a>)</p>

---

<div align="center">
  Made with ❤️ by UNC Software Engineering Students
</div>
<div align="center">
  In VTuple we trust
</div>

<!-- MARKDOWN LINKS & IMAGES -->
[contributors-shield]: https://img.shields.io/github/contributors/juanma-gil/soft-eng-2026-final-project.svg?style=for-the-badge
[contributors-url]: https://github.com/juanma-gil/soft-eng-2026-final-project/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/juanma-gil/soft-eng-2026-final-project.svg?style=for-the-badge
[forks-url]: https://github.com/juanma-gil/soft-eng-2026-final-project/network/members
[stars-shield]: https://img.shields.io/github/stars/juanma-gil/soft-eng-2026-final-project.svg?style=for-the-badge
[stars-url]: https://github.com/juanma-gil/soft-eng-2026-final-project/stargazers
[issues-shield]: https://img.shields.io/github/issues/juanma-gil/soft-eng-2026-final-project.svg?style=for-the-badge
[issues-url]: https://github.com/juanma-gil/soft-eng-2026-final-project/issues
[license-shield]: https://img.shields.io/github/license/juanma-gil/soft-eng-2026-final-project.svg?style=for-the-badge
[license-url]: https://github.com/juanma-gil/soft-eng-2026-final-project/blob/main/LICENSE

[Spring-badge]: https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white
[Spring-url]: https://spring.io/projects/spring-boot
[Flask-badge]: https://img.shields.io/badge/Flask-000000?style=for-the-badge&logo=flask&logoColor=white
[Flask-url]: https://flask.palletsprojects.com/
[NestJS-badge]: https://img.shields.io/badge/NestJS-E0234E?style=for-the-badge&logo=nestjs&logoColor=white
[NestJS-url]: https://nestjs.com/
[Next-badge]: https://img.shields.io/badge/Next.js-000000?style=for-the-badge&logo=next.js&logoColor=white
[Next-url]: https://nextjs.org/
[PostgreSQL-badge]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org/
[Docker-badge]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://www.docker.com/
[ESP32-badge]: https://img.shields.io/badge/ESP32-000000?style=for-the-badge&logo=espressif&logoColor=white
[ESP32-url]: https://www.espressif.com/en/products/socs/esp32
[PlatformIO-badge]: https://img.shields.io/badge/PlatformIO-FF7F00?style=for-the-badge&logo=data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjUwMCIgaGVpZ2h0PSIyNTAwIiB2aWV3Qm94PSIwIDAgMjU2IDI1NiIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiBwcmVzZXJ2ZUFzcGVjdFJhdGlvPSJ4TWlkWU1pZCI+PHBhdGggZD0iTTEyOCAwQzkzLjgxIDAgNjEuNjY2IDEzLjMxNCAzNy40OSAzNy40OSAxMy4zMTQgNjEuNjY2IDAgOTMuODEgMCAxMjhjMCAzNC4xOSAxMy4zMTQgNjYuMzM0IDM3LjQ5IDkwLjUxQzYxLjY2NiAyNDIuNjg2IDkzLjgxIDI1NiAxMjggMjU2YzM0LjE5IDAgNjYuMzM0LTEzLjMxNCA5MC41MS0zNy40OUMyNDIuNjg2IDE5NC4zMzQgMjU2IDE2Mi4xOSAyNTYgMTI4YzAtMzQuMTktMTMuMzE0LTY2LjMzNC0zNy40OS05MC41MUMxOTQuMzM0IDEzLjMxNCAxNjIuMTkgMCAxMjggMHptMCAxOC4wNzJjNjAuNDcxIDAgMTA5LjkyOCA0OS40NTcgMTA5LjkyOCAxMDkuOTI4UzE4OC40NzEgMjM3LjkyOCAxMjggMjM3LjkyOGMtNjAuNDcxIDAtMTA5LjkyOC00OS40NTctMTA5LjkyOC0xMDkuOTI4UzY3LjUyOSAxOC4wNzIgMTI4IDE4LjA3MnpNMTI4IDU3LjYxNGMtMzguNDc1IDAtNzAuMzg2IDMxLjkxMS03MC4zODYgNzAuMzg2IDAgMzguNDc1IDMxLjkxMSA3MC4zODYgNzAuMzg2IDcwLjM4NiAzOC40NzUgMCA3MC4zODYtMzEuOTExIDcwLjM4Ni03MC4zODYgMC0zOC40NzUtMzEuOTExLTcwLjM4Ni03MC4zODYtNzAuMzg2em0wIDE4LjA3MmMzMC43NDkgMCA1Mi4zMTQgMjEuNTY1IDUyLjMxNCA1Mi4zMTQgMCAzMC43NDktMjEuNTY1IDUyLjMxNC01Mi4zMTQgNTIuMzE0LTMwLjc0OSAwLTUyLjMxNC0yMS41NjUtNTIuMzE0LTUyLjMxNCAwLTMwLjc0OSAyMS41NjUtNTIuMzE0IDUyLjMxNC01Mi4zMTR6IiBmaWxsPSIjRkZGIi8+PC9zdmc+
[PlatformIO-url]: https://platformio.org/

