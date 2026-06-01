# LifeSamadhan — Unified Local Service Provider Management System

A full-stack, multi-backend service management platform that connects customers with local service providers. The system supports service booking, automatic provider assignment, OTP-based service verification, Razorpay payment integration, real-time notifications, and a rating system.

Built as a microservice-style architecture with **two interchangeable backends** (ASP.NET Core Web API and Spring Boot) sharing the same React frontend and MySQL database.

---

## Table of Contents

- [Technologies Used](#technologies-used)
- [Architecture Overview](#architecture-overview)
- [Project Structure](#project-structure)
- [Features by Role](#features-by-role)
- [Database Schema](#database-schema)
- [API Documentation](#api-documentation)
- [Setup & Installation](#setup--installation)
- [Environment Configuration](#environment-configuration)
- [Default Login Credentials](#default-login-credentials)

---

## Technologies Used

### Frontend

| Technology | Version | Purpose |
|---|---|---|
| React.js | 19.2 | UI framework |
| Vite | 7.2 | Build tool & dev server |
| React Router DOM | 7.13 | Client-side routing |
| Axios | 1.13 | HTTP client |
| SignalR Client | 10.0 | Real-time notifications (.NET backend) |
| SockJS + StompJS | 1.6 / 2.3 | Real-time notifications (Spring Boot backend) |

### Backend 1 — ASP.NET Core Web API

| Technology | Version | Purpose |
|---|---|---|
| .NET | 8.0 | Runtime |
| ASP.NET Core | 8.0 | Web API framework |
| Entity Framework Core | 8.0.4 | ORM |
| Pomelo.EntityFrameworkCore.MySql | 8.0.2 | MySQL provider for EF Core |
| JWT Bearer Authentication | 8.0.4 | Token-based auth |
| SignalR | Built-in | Real-time WebSocket notifications |
| Razorpay .NET SDK | 3.1.2 | Payment gateway |
| Swagger / Swashbuckle | 6.5.0 | API documentation |

### Backend 2 — Spring Boot

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Runtime |
| Spring Boot | 3.2.1 | Web framework |
| Spring Data JPA | 3.2.1 | ORM |
| Spring Security | 3.2.1 | Authentication & authorization |
| MySQL Connector | 8.0.33 | Database driver |
| JJWT | 0.12.3 | JWT token handling |
| Spring WebSocket + STOMP | 3.2.1 | Real-time notifications |
| Razorpay Java SDK | 1.4.3 | Payment gateway |
| SpringDoc OpenAPI | 2.3.0 | Swagger UI |
| Lombok | Latest | Boilerplate reduction |
| ModelMapper | 3.2.0 | DTO mapping |

### Database

| Technology | Purpose |
|---|---|
| MySQL | Primary relational database |

---

## Architecture Overview

```
┌──────────────────────────────────────────────────────┐
│                    React Frontend                     │
│         (Vite + React Router + Axios)                │
│                                                      │
│  ┌──────────┐  ┌──────────┐  ┌───────────────────┐  │
│  │  Admin   │  │ Customer │  │ Service Provider  │  │
│  │Dashboard │  │Dashboard │  │   Dashboard       │  │
│  └──────────┘  └──────────┘  └───────────────────┘  │
│         │              │              │              │
│         └──────────────┼──────────────┘              │
│                        │ REST API + WebSocket        │
└────────────────────────┼─────────────────────────────┘
                         │
          ┌──────────────┴──────────────┐
          │      Switchable Backend     │
          │   (Toggle via .env file)    │
          │                             │
    ┌─────┴──────┐            ┌────────┴────────┐
    │ ASP.NET    │            │   Spring Boot   │
    │ Core 8.0   │            │     3.2.1       │
    │ + SignalR  │            │  + WebSocket    │
    │ + EF Core  │            │  + Spring JPA   │
    └─────┬──────┘            └────────┬────────┘
          │                            │
          └────────────┬───────────────┘
                       │
              ┌────────┴────────┐
              │     MySQL       │
              │  lifesamadhan   │
              │     _db_v2      │
              └────────┬────────┘
                       │
              ┌────────┴────────┐
              │   Razorpay      │
              │  Payment API    │
              └─────────────────┘
```

---

## Project Structure

```
LifeSamadhan/
├── Frontend/                          # React + Vite frontend
│   ├── src/
│   │   ├── api/                       # API layer (auth, admin, customer, provider, payment)
│   │   ├── components/
│   │   │   ├── auth/                  # ProtectedRoute, RoleRoute
│   │   │   └── common/               # Navbar, NotificationManager
│   │   ├── context/                   # AuthContext (JWT state management)
│   │   ├── pages/
│   │   │   ├── Home.jsx               # Landing page
│   │   │   ├── Login.jsx              # Login page
│   │   │   ├── Register.jsx           # Multi-role registration
│   │   │   ├── AboutUs.jsx            # About us page
│   │   │   ├── ContactUs.jsx          # Contact page
│   │   │   ├── admin/                 # Admin dashboard, categories, services, providers, etc.
│   │   │   ├── customer/              # Customer dashboard (book, track, rate, pay)
│   │   │   └── provider/              # Provider dashboard (accept, OTP, complete)
│   │   ├── services/                  # SignalR & WebSocket notification services
│   │   ├── styles/                    # Global CSS
│   │   └── utils/                     # Axios instance with interceptors
│   ├── .env                           # Backend type toggle (DOTNET / SPRINGBOOT)
│   ├── package.json
│   └── vite.config.js
│
├── LifeSamadhan/Backend/              # ASP.NET Core 8.0 Web API
│   ├── Controllers/
│   │   ├── AuthController.cs          # Register, Login
│   │   ├── AdminController.cs         # User & provider management, dashboard stats
│   │   ├── CustomerController.cs      # Service requests, assignments, ratings, profile
│   │   ├── ProviderController.cs      # Assignments, OTP, earnings
│   │   ├── PaymentController.cs       # Razorpay order creation & verification
│   │   ├── RatingController.cs        # Rating submission
│   │   ├── ServiceController.cs       # CRUD for services
│   │   ├── ServiceCategoryController.cs
│   │   ├── ProviderTypeController.cs
│   │   ├── ProviderSkillController.cs
│   │   ├── LocationController.cs
│   │   ├── ProviderLocationController.cs
│   │   └── PublicController.cs        # Public endpoints (locations, provider search)
│   ├── Models/                        # 20 entity/DTO models
│   ├── Data/                          # EF Core DbContext
│   ├── Services/                      # Business logic (JWT, Email, Assignment, Cleanup)
│   ├── Hubs/                          # SignalR NotificationHub
│   ├── Migrations/                    # EF Core migrations
│   ├── Scripts/                       # SeedAdmin.sql
│   ├── Program.cs                     # Application entry point
│   └── appsettings.json               # DB connection, JWT, Razorpay, Email config
│
├── lifeFull/SpringBootLifeSamadhan/   # Spring Boot 3.2.1 Backend (alternate)
│   ├── src/main/java/com/lifesamadhan/
│   │   ├── controller/                # REST controllers
│   │   ├── model/                     # JPA entities
│   │   ├── repository/                # Spring Data JPA repositories
│   │   ├── service/                   # Business logic layer
│   │   ├── config/                    # Security, WebSocket, CORS config
│   │   └── dto/                       # Request/Response DTOs
│   ├── pom.xml                        # Maven dependencies
│   └── application.properties         # Configuration
│
├── README.md
└── TEAM_25_SRS_Final_Project.pdf      # Software Requirements Specification document
```

---

## Features by Role

### Admin
- Dashboard with platform-wide statistics (users, providers, requests, revenue)
- Manage **Service Categories** (CRUD)
- Manage **Services** under categories (CRUD with pricing)
- Manage **Provider Types** (e.g., Plumber, Electrician) with active/inactive toggle
- Manage **Locations** (city, state, pincode — CRUD)
- View & manage **all registered providers** (verify, activate, deactivate, delete)
- Approve/reject provider **skill requests**
- View all registered users

### Customer
- Browse services by category and location
- Search for available providers by category and city
- **Book a service** — creates a service request with automatic provider assignment
- Track service request status in real-time
- Receive **OTP** upon provider assignment for service verification
- **Rate** completed services (1–5 stars with review)
- **Online payment** via Razorpay after service completion
- View booking history and manage profile
- **Real-time notifications** (via SignalR / WebSocket)

### Service Provider
- Register with provider type, skills, hourly rate, and service areas
- Receive **new assignment notifications** in real-time
- Accept or reject incoming service assignments
- **Verify OTP** from customer before starting service
- Mark service as **completed**
- View **earnings** and payment history
- Manage skills (request new skill approvals from admin)

---

## Database Schema

The MySQL database `lifesamadhan_db_v2` contains the following tables:

| Table | Description |
|---|---|
| `users` | All users (Admin, Customer, Service Provider) with role-based access |
| `customer_profiles` | Extended profile info for customers |
| `service_providers` | Provider details — hourly rate, verification status, user FK |
| `provider_types` | Types of providers (Plumber, Electrician, etc.) |
| `provider_skills` | Skills mapped to providers with approval status |
| `provider_locations` | Service areas where providers operate |
| `service_categories` | Categories grouping related services |
| `services` | Individual services under categories |
| `service_prices` | Pricing for services (per-service or hourly) |
| `service_requests` | Customer booking requests with status tracking |
| `service_assignments` | Provider assignments linked to requests (OTP, status) |
| `ratings` | Customer ratings for completed services (1–5 stars) |
| `payments` | Razorpay payment records (order ID, payment ID, status) |
| `locations` | Platform-wide location directory (city, state, pincode) |
| `notifications` | In-app notification records |
| `support_tickets` | Customer support tickets |

---

## API Documentation

### Authentication APIs

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/auth/register` | Register a new user (Customer/Provider/Admin) | No |
| `POST` | `/api/auth/login` | Login and receive JWT token | No |

### Admin APIs (Admin only)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/admin/dashboard/stats` | Get platform statistics |
| `GET` | `/api/admin/users` | List all registered users |
| `GET` | `/api/admin/providers` | List all providers (filter by verified status) |
| `PUT` | `/api/admin/provider/:id/verify` | Verify a provider |
| `PUT` | `/api/admin/provider/:id/status` | Update provider status (active/inactive) |
| `DELETE` | `/api/admin/provider/:id` | Delete a provider |
| `GET` | `/api/admin/skills/pending` | List pending skill approval requests |
| `PUT` | `/api/admin/skill/:id/approve` | Approve a skill request |
| `PUT` | `/api/admin/skill/:id/reject` | Reject a skill request |

### Service Category & Service APIs

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `GET` | `/api/category` | List all categories | Yes |
| `POST` | `/api/category` | Create a new category | Admin |
| `PUT` | `/api/category/:id` | Update a category | Admin |
| `DELETE` | `/api/category/:id` | Delete a category | Admin |
| `GET` | `/api/service` | List all services | Yes |
| `GET` | `/api/service/by-category/:id` | Get services by category | Yes |
| `POST` | `/api/service` | Create a new service | Admin |
| `PUT` | `/api/service/:id` | Update a service | Admin |
| `DELETE` | `/api/service/:id` | Delete a service | Admin |

### Provider Type APIs

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `GET` | `/api/providertype` | List all provider types | Admin |
| `GET` | `/api/providertype/active` | List active provider types | No |
| `POST` | `/api/providertype` | Create provider type | Admin |
| `PUT` | `/api/providertype/:id` | Update provider type | Admin |
| `DELETE` | `/api/providertype/:id` | Delete provider type | Admin |

### Location APIs

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `GET` | `/api/location` | List all locations | Admin |
| `POST` | `/api/location` | Create a location | Admin |
| `PUT` | `/api/location/:id` | Update a location | Admin |
| `DELETE` | `/api/location/:id` | Delete a location | Admin |

### Public APIs (No Auth)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/public/locations` | List all locations |
| `GET` | `/api/public/locations/active` | List active locations |
| `GET` | `/api/public/providers/search` | Search providers by category and city |

### Customer APIs (Customer only)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/customer/request` | Create a new service request |
| `GET` | `/api/customer/requests` | Get my service requests |
| `GET` | `/api/customer/assignments` | Get my assignments |
| `POST` | `/api/customer/cancel/:assignmentId` | Cancel a service request |
| `POST` | `/api/customer/rating/:requestId` | Submit a rating |
| `GET` | `/api/customer/profile` | Get my profile |
| `PUT` | `/api/customer/profile` | Update my profile |

### Service Provider APIs (Provider only)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/provider/assignments` | Get my assignments |
| `POST` | `/api/provider/assignment/:id/accept` | Accept an assignment |
| `POST` | `/api/provider/assignment/:id/reject` | Reject an assignment |
| `POST` | `/api/provider/assignment/:id/start` | Start service (OTP verification) |
| `POST` | `/api/provider/assignment/:id/complete` | Mark service as completed |
| `GET` | `/api/provider/earnings` | Get earnings summary |

### Payment APIs (Razorpay)

| Method | Endpoint | Description | Auth |
|---|---|---|---|
| `POST` | `/api/payment/create-order/:assignmentId` | Create Razorpay order | Customer |
| `POST` | `/api/payment/verify` | Verify Razorpay payment | Customer |

### Real-time Notifications

| Backend | Protocol | Endpoint |
|---|---|---|
| ASP.NET Core | SignalR (WebSocket) | `/hubs/notifications` |
| Spring Boot | STOMP over SockJS | `/ws` |

---

## Setup & Installation

### Prerequisites

- **Node.js** (v18+) and npm
- **MySQL** (v8.0+)
- **.NET 8 SDK** (for ASP.NET Core backend) OR **Java 17 + Maven** (for Spring Boot backend)

### 1. Clone the Repository

```bash
git clone https://github.com/dipakfirake/LifeSamadhan-Unified-Local-Service-Provider-Management-System.git
cd LifeSamadhan-Unified-Local-Service-Provider-Management-System
```

### 2. Database Setup

```bash
# Create the database
mysql -u root -p -e "CREATE DATABASE lifesamadhan_db_v2;"
```

The tables are auto-generated by EF Core Migrations (.NET) or Spring JPA (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Backend Setup

#### Option A: ASP.NET Core Backend

```bash
cd LifeSamadhan/Backend

# Update connection string in appsettings.json if needed
# Then run:
dotnet restore
dotnet run
```

The API will start at `http://localhost:5055` with Swagger UI at `/swagger`.

#### Option B: Spring Boot Backend

```bash
cd lifeFull/SpringBootLifeSamadhan

# Update application.properties with your MySQL credentials
# Then run:
mvn spring-boot:run
```

The API will start at `http://localhost:8080` with Swagger UI at `/swagger-ui.html`.

### 4. Frontend Setup

```bash
cd Frontend
npm install
npm run dev
```

The frontend will start at `http://localhost:5173`.

### 5. Switch Between Backends

Edit `Frontend/.env` to toggle the active backend:

```env
# Use ASP.NET Core backend
VITE_BACKEND_TYPE=DOTNET

# Or use Spring Boot backend
# VITE_BACKEND_TYPE=SPRINGBOOT
```

---

## Environment Configuration

### ASP.NET Core (`appsettings.json`)

```json
{
  "ConnectionStrings": {
    "Default": "server=localhost;port=3306;database=lifesamadhan_db_v2;user=root;password=your_password;"
  },
  "Jwt": {
    "Key": "your_jwt_secret_key",
    "Issuer": "LifeSamadhan.API",
    "Audience": "LifeSamadhan.Client",
    "ExpireMinutes": "120"
  },
  "Razorpay": {
    "KeyId": "your_razorpay_key_id",
    "KeySecret": "your_razorpay_key_secret"
  },
  "EmailSettings": {
    "Email": "your_email@gmail.com",
    "Password": "your_app_password",
    "Host": "smtp.gmail.com",
    "Port": 587,
    "EnableSsl": true
  }
}
```

### Spring Boot (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lifesamadhan_db_v2
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secret=your_jwt_secret_key
jwt.expiration=7200000

razorpay.key.id=your_razorpay_key_id
razorpay.key.secret=your_razorpay_key_secret
```

---

## Default Login Credentials

| Role | Email | Password |
|---|---|---|
| Admin | `admin@lifesamadhan.com` | `Admin@123` |

To create the admin user, use the registration API:

```bash
POST http://localhost:5055/api/auth/register
Content-Type: application/json

{
  "name": "Admin",
  "email": "admin@lifesamadhan.com",
  "mobile": "9999999999",
  "password": "Admin@123",
  "role": "ADMIN"
}
```

New customers and service providers can register through the platform's registration page.

---

## Key Highlights

- **Dual-backend architecture** — swap between ASP.NET Core and Spring Boot without touching the frontend
- **Auto-assignment engine** — automatically assigns the best available provider based on skills, location, and ratings
- **OTP verification** — ensures only authorized providers start the service
- **Razorpay integration** — secure online payments after service completion
- **Real-time notifications** — SignalR (for .NET) and WebSocket/STOMP (for Spring Boot)
- **Role-based access control** — JWT authentication with admin, customer, and provider roles
- **Timed cleanup service** — background job that auto-cancels unattended requests
- **Email notifications** — sends OTP and status updates via SMTP
- **Swagger/OpenAPI** — interactive API documentation for both backends

---

## License

This project was developed as part of an academic final project (Team 25). See `TEAM_25_SRS_Final_Project.pdf` for the complete Software Requirements Specification.
