# Reservily

This project is a microservices-based table reservation system built with Spring Boot and PostgreSQL. It includes services for user management, restaurant management, an API gateway, and Keycloak for authentication and authorization.

---

## Prerequisites

Docker & Docker Compose installed

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/aralghamdi/reservily.git
```

```bash
cd table-reservation
```

### 2. Build and Start All Services
```bash
docker-compose up --build
```

### 3. Run Schema Initialization Script
```bash
cd .\config\postgres\
```
```bash
bash init-schema.sh
```


### 4. Import postman collection
Import the Postman collection provided in the repository
Set the host variable to:
http://host.docker.internal:8083

### 4. Initialize Admin User
For the purpose of this assignment, you can create the first admin user by calling the following endpoint:
```bash
POST http://localhost:8083/api/v1/user/init-admin
```
