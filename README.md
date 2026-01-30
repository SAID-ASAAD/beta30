# β30 - Business Management System

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green.svg)](https://spring.io/projects/spring-security)
[![Docker](https://img.shields.io/badge/Docker-supported-blue.svg)](https://www.docker.com)

B30 is a comprehensive management system designed for small to medium-sized jewelry artisan, focusing on the control of clients, orders, inventory, sales, and finances. Built with a modern Java and Spring Boot stack, the system provides a secure, scalable, and maintainable solution for business administration.

## Key Features

- **Client Management**: Full CRUD operations for clients, with an integrated history of their orders and product purchases.
- **Product & Inventory Control**: Stock management with automatic reduction on sales and restocking on cancellations. Tracks material costs, external service costs, and suggested pricing.
- **Order Management**: A complete order status workflow (in-progress, completed, canceled) with financial tracking (deposits, payments).
- **Sales & Payments**: Supports multiple partial sales from a single product batch, with individual payment tracking for each sale.
- **Advanced Financial Dashboard**:
  - **Real-time Analytics**: Displays total revenue, accounts receivable, net profit, and total expenses.
  - **Date Filtering**: All metrics can be filtered by a custom date range.
  - **Detailed Drill-Down**: Interactive cards allow users to view detailed lists of payments, pending receivables, and expenses.
  - **Profit Analysis**: Provides three levels of profit analysis: Net Profit (Revenue - Expenses), Gross Profit (Revenue - Direct Costs), and a detailed per-item profit breakdown.

## Technical Architecture

The system is built on a layered architecture using the Spring Boot ecosystem, ensuring a clear separation of concerns.

- **Backend**:
  - **Java 21 & Spring Boot 3**: Core framework.
  - **Spring Data JPA (Hibernate)**: Persistence layer.
  - **PostgreSQL**: Production-ready relational database.
  - **Spring Security 6**: Authentication and authorization.
  - **MapStruct**: High-performance mapping between entities and DTOs.
  - **Lombok**: Reduces boilerplate code.

- **Frontend**:
  - **Thymeleaf**: Server-side template engine for dynamic HTML rendering.
  - **Bootstrap 5**: Responsive UI components.
  - **JavaScript & jQuery**: Client-side interactivity, including AJAX calls for the financial dashboard and input masking.
  - **Inputmask.js**: Provides a seamless user experience for currency inputs.

### Security Architecture

Security is a core component of the B30 system, designed to demonstrate modern best practices:

1.  **Stateless Authentication with JWT**: User sessions are managed via signed JSON Web Tokens stored in `HttpOnly` cookies. This approach is secure against XSS attacks and prepares the system for a decoupled frontend architecture.
2.  **Sliding Session Mechanism**: A custom `SecurityFilter` automatically renews the JWT upon user activity, ensuring a good user experience without compromising the security of short-lived tokens.
3.  **CSRF Protection**: Integrated with Spring Security to protect all state-changing form submissions.
4.  **Password Hashing**: User passwords are securely hashed using **BCrypt**.
5.  **Cache Control**: HTTP headers are configured to prevent browser caching of sensitive pages, mitigating data exposure after logout.

## Running the Application with Docker

The simplest and most reliable way to run the B30 system is with Docker, which handles the application and the database setup automatically.

**Prerequisites:**
- **Docker Desktop** installed.

**Instructions:**

1.  **Clone or download** the project repository.
2.  **Open a terminal** in the project's root directory.
3.  **Run the following command**:
    ```sh
    docker compose up --build
    ```
    This command will build the Java application, create a PostgreSQL database, and start both services. The first run may take a few minutes.

4.  **Access the application** by navigating to `http://localhost:8080` in your web browser.

**To stop the application:**
```sh
docker compose down
```

### For macOS Users (Click-to-Run)

The project includes two executable scripts for convenience:
- `iniciar.command`: Double-click to start the system.
- `parar.command`: Double-click to stop the system.

*Note: You may need to grant execution permissions first by running `chmod +x iniciar.command parar.command` in the terminal.*
