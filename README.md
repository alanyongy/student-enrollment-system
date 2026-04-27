# Course Registration System (Backend)

Spring Boot backend for a Course Registration System.

## Quick Start Checklist

- [ ] Clone the repository
- [ ] Install Java 21
- [ ] Install and run MySQL Server (Workbench is optional but helpful)
- [ ] Create database `appdb`
- [ ] Set your local MySQL username/password in `src/main/resources/application.yaml`
- [ ] Run the application with Maven Wrapper

## 1) Clone the project

### HTTPS
```bash
git clone https://csgit.ucalgary.ca/prateek.babani/course-registration-backend.git
cd CourseRegistrationSystem
```

### SSH
```bash
git clone git@csgit.ucalgary.ca:prateek.babani/course-registration-backend.git
cd CourseRegistrationSystem
```

## 2) Prerequisites

- **Java:** 21 (project uses `java.version=21` in `pom.xml`)
- **MySQL Server:** 8.x recommended
- **MySQL Workbench:** optional GUI for managing MySQL

> Note: Workbench is not mandatory. The backend needs MySQL Server running.

## 3) Create the database

Run in MySQL:

```sql
CREATE DATABASE appdb;
```

## 4) Configure database credentials

Open `src/main/resources/application.yaml` and set your local credentials:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/appdb
    username: root
    password: <your-local-mysql-password>
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### Are you correct about `root` and password in YAML?

Yes, for local development this is fine and commonly used.

- `username: root` works if your MySQL root user is enabled and allowed to connect.
- `password` should be your local MySQL password.

Recommended improvement (later): use environment variables instead of hardcoding passwords.

Example:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/appdb
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:}
```

## 5) Run the backend

### Windows (PowerShell / CMD)
```powershell
.\mvnw.cmd clean spring-boot:run
```

### macOS / Linux
```bash
./mvnw clean spring-boot:run
```

The app starts on:

- `http://localhost:8080`

## 6) Optional: run tests

### Windows
```powershell
.\mvnw.cmd test
```

### macOS / Linux
```bash
./mvnw test
```

## Important notes

- Current config has `spring.jpa.hibernate.ddl-auto: create`, so schema is recreated on startup.
- `spring.sql.init.mode: always` means SQL init scripts run each start.
- Do not commit real passwords to Git in shared/public repositories.
