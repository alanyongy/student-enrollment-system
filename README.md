# 🎓 Course Registration System (Full-Stack Web Application)

![Login Page](/writeup-assets/combined-preview.png)

A database-driven course registration platform that models real-world academic enrollment systems with support for students, administrators, programs, prerequisites, and section management.

Built as a full-stack application using **Spring Boot**, **React**, and **MySQL**, this project demonstrates relational database design, role-based access control, and constraint-driven enrollment logic.

## 🧩 Overview

The Course Registration System simulates how universities manage course offerings and student enrollment at scale.

It supports two primary user roles:

- **Students** — browse courses, view prerequisites, enroll/drop sections, and track academic progress  
- **Administrators** — manage institutional data including courses, programs, sections, instructors, and student records  

The system enforces key academic constraints such as:
- prerequisite requirements
- program restrictions
- section capacity and structure rules
- role-based access permissions

## ⚙️ Tech Stack

- **Frontend:** React  
- **Backend:** Spring Boot (Java)  
- **Database:** MySQL  
- **Architecture:** RESTful API + Relational Database Design  
- **Security:** Role-Based Access Control (RBAC)

## 🎯 Key Features

### Student Features
- Login / logout authentication
- View programs and courses
- View course prerequisites
- Browse available sections by semester
- Enroll in / drop courses
- View enrolled and completed courses

### Administrator Features
- Manage courses and prerequisites
- Create and update programs and departments
- Manage sections and semesters
- Assign instructors to sections
- Manage student records and admissions

## 🖼️ System Screenshots

### 🔐 Login Page
![Login Page](/writeup-assets/login.png)

### 🎓 Student Dashboard
![Student Dashboard](/writeup-assets/student-dashboard.png)

![Student Dashboard 2](/writeup-assets/student-dashboard2.png)

### 👤 Student Profile
![Student Profile](/writeup-assets/student-profile.png)

### 🛠️ Administrator Dashboard
![Admin Dashboard](/writeup-assets/admin-dashboard.png)

### 📚 Course Creation (Admin)
![Course Creation](/writeup-assets/admin-course-creation.png)

### 🧾 Enrollment Table (Admin)
![Enrollment Table](/writeup-assets/admin-enrollment-table.png)

### 📱 Admin Mobile View
![Admin Mobile View](/writeup-assets/admin-mobile-view.png)

## 🏗️ System Design

The system is built around a relational model representing a realistic academic environment.

### Conceptual ER Model
![ER Diagram](/writeup-assets/EERD.png)

### Relational Model
![Relational Model](/writeup-assets/RM.png)

Core entities include:
- Students
- Instructors
- Courses
- Sections
- Programs
- Departments
- Semesters
- Enrollments

Key design principles:
- Normalized relational schema
- Strict foreign key constraints
- Separation of concerns between academic structure and enrollment logic
- Clear mapping between ER design and relational schema implementation

## 🔐 Role-Based Access Control (RBAC)

The system enforces strict role separation:

- **Students** can only access their own academic data and registration actions
- **Administrators** have full control over institutional data and system configuration

Each user’s role is stored in the database and validated on every protected request.

This ensures:
- data privacy
- prevention of unauthorized modifications
- clean separation of system responsibilities


## 🧠 Core System Logic

### Enrollment Constraints
Students can only enroll if:
- prerequisites are completed
- the course is available in their program
- section capacity rules are satisfied

### Administrative Control Flow
Administrators define:
- which courses exist
- which programs they belong to
- when and how courses are offered (sections)
- which instructors teach them

Students interact with this structure indirectly through enrollment actions.

## 🗄️ Database Design

The system uses a fully relational MySQL schema with:

- One-to-many relationships (e.g., course → sections)
- Many-to-many relationships (e.g., students ↔ sections via enrollments)
- Strong referential integrity via primary/foreign keys
- Constraint-driven design for academic rules

The schema is derived from an ER model and converted into a normalized relational schema following standard database design principles.

## 🚀 Implementation Highlights

- REST API built with Spring Boot for modular backend services
- React frontend for dynamic UI and student/admin dashboards
- Authentication and authorization layer enforcing RBAC
- Pagination and sorting for scalable course browsing
- Structured enrollment workflow with validation at API level
- MySQL-backed persistence layer with relational constraints

## 📊 Project Significance

This system demonstrates:
- real-world database modeling (academic institution structure)
- full-stack application architecture
- enforcement of complex business rules in backend logic
- secure role-based system design
- practical implementation of relational constraints at scale

It mirrors the kind of systems used in enterprise platforms such as:
- Oracle PeopleSoft Campus Solutions
- Ellucian Banner

but implemented in a simplified, educational form focused on clarity and core functionality.

## 👥 Group Project Note

This was a **collaborative academic project**.

My contributions include:
- backend API design and implementation (Spring Boot)
- database schema design and relational modeling
- enforcement of enrollment constraints and business logic
- role-based access control implementation
- integration between backend and frontend systems
- full administrator frontend implementation

## 📁 Project Structure (Simplified)

- /backend → Spring Boot REST API  
- /frontend → React UI  
- /database → MySQL schema & setup scripts  
- /docs → Project report and ERD/RM diagrams  

## ⚠️ Limitations

- Designed for academic scale (not production load-tested)
- No deployment pipeline included (local setup only)
- Limited UI polish compared to production enrollment systems
- Simplified concurrency handling for enrollment operations

## 📌 Lessons Learned

- Designing normalized relational schemas for real-world systems
- Translating ER diagrams into implementable database structures
- Handling role-based permissions across frontend/backend boundaries
- Building APIs that enforce business rules consistently
- Structuring full-stack applications with clean separation of concerns

## 🔧 Future Improvements

- Deployment to cloud (AWS / Azure / GCP)
- Improved concurrency handling for enrollment spikes
- Enhanced UI/UX for course browsing and registration
- Advanced search/filtering for course discovery
- Audit logging for administrative actions

## ⚙️ How to Run

> <details>
> <summary><strong>Click to Expand</strong></summary>
> 
> ### Prerequisites
> 
> Install the following software before running the project:
> 
> - Java Development Kit (JDK) 21
> - MySQL Server (8.x recommended)
> - Node.js + npm
> - *(Optional)* MySQL Workbench
> 
> ---
> 
> ## Clone the Repository
> 
> ```bash
> git clone <repository-url>
> cd <repository-name>
> ```
> 
> ---
> 
> ## Backend Setup
> 
> The backend source code is located in:
> 
> ```text
> /backend
> ```
> 
> ### 1. Create the Database
> 
> ```sql
> CREATE DATABASE appdb;
> ```
> 
> ---
> 
> ### 2. Configure Database Credentials
> 
> ```text
> /backend/src/main/resources/application.yaml
> ```
> 
> ```yaml
> spring:
>   datasource:
>     url: jdbc:mysql://localhost:3306/appdb
>     username: root
>     password: <your-password>
> ```
> 
> ---
> 
> ### 3. Run the Backend
> 
> ```bash
> cd backend
> .\mvnw.cmd clean spring-boot:run   # Windows
> ./mvnw clean spring-boot:run       # Mac/Linux
> ```
> 
> Backend runs at:
> ```
> http://localhost:8080
> ```
> 
> ---
> 
> ## Frontend Setup
> 
> ```bash
> cd frontend
> npm install
> npm run dev
> ```
> 
> Frontend runs at:
> ```
> http://localhost:5173
> ```
> 
> ---
> 
> ## Test Accounts
> 
> **Student**
> ```
> student@example.com / student123
> ```
> 
> **Admin**
> ```
> admin@example.com / admin123
> ```
> 
> ---
> 
> ## Notes
> 
> - Backend must run before frontend
> - DB is recreated on startup (`ddl-auto: create`)
> - Seed data loads from `data.sql`
> - Do not expose real DB credentials publicly
> 
> </details>
