# Spring Boot JWT Authentication with Role-Based Access Control

A complete Spring Boot application implementing JWT token authentication and role-based authorization using Spring Security.

## Features

- JWT token generation and validation
- User registration and login
- Role-based access control (USER and ADMIN roles)
- Spring Security integration
- PostgreSQL database support
- RESTful API endpoints

## Technologies Used

- Spring Boot 3.2.0
- Spring Security
- JWT (JSON Web Tokens)
- JPA/Hibernate
- PostgreSQL
- Lombok
- Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL database

## Configuration

Update the `.env` file with your database credentials:

```properties
SUPABASE_DB_URL=jdbc:postgresql://your-host:5432/your-database
JWT_SECRET=your_jwt_secret_key_minimum_256_bits_for_hs256_algorithm
JWT_EXPIRATION=86400000
```

## Database Setup

Run the SQL migration file to create the users table:

```sql
-- Located in: src/main/resources/db/migration/V1__create_users_table.sql
```

## API Endpoints

### Authentication Endpoints (Public)

#### Register
```
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "USER"
}
```

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "username": "john_doe",
  "role": "USER",
  "message": "Login successful"
}
```

### User Endpoints (Requires USER or ADMIN role)

#### Get User Profile
```
GET /api/user/profile
Authorization: Bearer {token}
```

#### Get User Dashboard
```
GET /api/user/dashboard
Authorization: Bearer {token}
```

### Admin Endpoints (Requires ADMIN role)

#### Get Admin Dashboard
```
GET /api/admin/dashboard
Authorization: Bearer {token}
```

#### Get All Users
```
GET /api/admin/users
Authorization: Bearer {token}
```

#### Get Statistics
```
GET /api/admin/stats
Authorization: Bearer {token}
```

## Building and Running

### Build the project
```bash
mvn clean install
```

### Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Testing with curl

### Register a new user
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "role": "USER"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

### Access protected endpoint
```bash
curl -X GET http://localhost:8080/api/user/profile \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Security Features

- Passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours (configurable)
- Stateless authentication using JWT
- Role-based access control using Spring Security annotations
- Separate endpoints for different roles (USER and ADMIN)

## Project Structure

```
src/main/java/com/example/jwtsecurity/
├── config/
│   └── SecurityConfig.java           # Spring Security configuration
├── controller/
│   ├── AuthController.java           # Authentication endpoints
│   ├── UserController.java           # User role endpoints
│   └── AdminController.java          # Admin role endpoints
├── dto/
│   ├── LoginRequest.java             # Login request payload
│   ├── RegisterRequest.java          # Registration request payload
│   └── AuthResponse.java             # Authentication response
├── filter/
│   └── JwtRequestFilter.java         # JWT validation filter
├── model/
│   └── User.java                     # User entity
├── repository/
│   └── UserRepository.java           # User data access
├── service/
│   ├── AuthService.java              # Authentication business logic
│   └── CustomUserDetailsService.java # User details for Spring Security
├── util/
│   └── JwtUtil.java                  # JWT utility methods
└── JwtSecurityApplication.java       # Main application class
```

## Default Roles

- **USER**: Can access user endpoints (`/api/user/**`)
- **ADMIN**: Can access both user and admin endpoints (`/api/user/**` and `/api/admin/**`)

## Notes

- The first admin user should be created by manually setting the role to 'ADMIN' in the database or through a secure initialization process
- JWT secret should be a strong, random string in production
- Token expiration time is configurable through application properties
