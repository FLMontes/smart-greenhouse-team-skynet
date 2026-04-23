# 🚀 ESP32 Firmware Project 

This section contains the base structure for the development of the Final Project firmware for the **Ingeniería de Software y Hardware** course of the Computer Engineering program at FCEFyN - UNC. The project is designed under an **aligned autonomy** approach, where teams have technical freedom within the engineering best practices established by the faculty.


## 📂 Project Structure

* **`include/`**: Contains header files (`.h`).
* **`src/`**: Directory for source code (`.cpp`). A modular division (sensors, communication, business logic) is recommended, aligned with the design and implementation process.
* **`test/`**: Unit Tests should be implemented here.
* **`.env.example`**: Template for managing sensitive configurations (SSID, passwords, backend IPs). **Note:** The actual `.env` file must never be uploaded to version control.
* **`platformio.ini`**: Configuration manifest that defines hardware, dependency management, and quality analysis tools.


## ⌨️ Useful Commands (PlatformIO CLI)

For managing the software life cycle, use the following commands from the terminal:

### Build and Upload Management
* **Compile the project:**
    `pio run`
* **Upload firmware to the ESP32:**
    `pio run -t upload`
* **Serial Monitor:**
    `pio device monitor`

### Quality and Testing
* **Run Unit Tests:**
    `pio test`
* **Static Code Analysis:**
    `pio check`
* **Clean temporary build files:**
    `pio run -t clean`

---
**Course:** Ingeniería de Software y Hardware - FCEFyN - UNC