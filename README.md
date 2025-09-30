# Veterinary Management System 🐾

A full-stack veterinary clinic management application built with Spring Boot and React.
This system streamlines day-to-day operations such as pet & owner management, appointments, medical records, and billing, while providing secure role-based access.

## 🚀 Features

* Owner & Pet Management – Add, update, and view owners and their pets with relational integrity.

* Appointments – Create, track, and update appointments with dashboard summaries of upcoming visits.

* Medical Records – Link records to specific pets and appointments for accurate health tracking.

* Invoices & Payments – Automatically generate invoices with real-time payment status and PDF export (soon).

* Authentication & Authorization – Role-based access (Admin/Staff) secured with JWT.

* Dashboard – View key metrics such as total customers, revenue, and daily appointments.

* Responsive UI – Built with React and Tailwind CSS for a clean, mobile-friendly experience.

## 🛠 Tech Stack

| Layer                    | Technology                         |
| ------------------------ | ---------------------------------- |
| **Frontend**             | React, Axios, Tailwind CSS         |
| **Backend**              | Spring Boot, Spring Security (JWT) |
| **Database**             | PostgreSQL                         |
| **Build**                | Maven                              |
| **Deployment (planned)** | Azure / Docker                     |

## 📂 Project Structure
```
veterinary-management-system/
│
├─ backend/                     # Spring Boot project
│  ├─ src/main/java/com/...     # Controllers, Services, Repositories, Entities
│  └─ src/main/resources/       # application.properties
│
├─ frontend/                     # React project
│  ├─ src/components/           # Reusable UI components
│  ├─ src/pages/                # Page-level components
│  └─ src/api/                  # Axios instance (api.js)
│
└─ README.md                     # Project documentation
```

## ⚡ Getting Started

### Prerequisites
* Java 17+

* Node.js 18+ & npm or yarn

* PostgreSQL

* Maven (for backend build)

### Backend Setup
```
cd backend
# Configure DB credentials in src/main/resources/application.properties
mvn clean install
mvn spring-boot:run
```
### 📄 `application-example.properties`
```
# ===============================
# Database Configuration
# ===============================
spring.datasource.url=jdbc:postgresql://localhost:5432/vet_clinic
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

```
> Setup:
>
> 1. Copy `src/main/resources/application-example.properties` to
`src/main/resources/application.properties`.
>
> 2. Replace placeholders (`YOUR_DB_USERNAME`, `YOUR_DB_PASSWORD`) with real values.
>
> 3. Run the backend using `mvn spring-boot:run` or your IDE.
   
### Frontend Setup
```
cd frontend
npm install
npm start
```

The React app will run on http://localhost:3000
,
and the Spring Boot API will run on http://localhost:8080
.

## 🔐 Default Admin Login
| Username | Password |
| -------- | -------- |
| `admin`  | `admin`  |

> ⚠️ Change credentials in production or connect to a real user database.

### Screenshots
<img width="975" height="473" alt="image" src="https://github.com/user-attachments/assets/a037ac61-e688-4f61-9ccf-80b0db72f7b5" />
<img width="975" height="474" alt="image" src="https://github.com/user-attachments/assets/f8fcf55e-8b10-45e9-98cf-683c2f3dd55a" />
<img width="975" height="476" alt="image" src="https://github.com/user-attachments/assets/390f728d-1dc8-4f07-bef7-2aaf9eb7754d" />


