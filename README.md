# JWT Authentication Backend

A backend authentication project focused on building a **JWT-based authentication system** using **Spring Boot, Spring Security, PostgreSQL**, and **Postman**.

The main goal of the project was to understand how JWT authentication works inside a Spring Boot application — from registering and authenticating a user, to validating a JWT and accessing a protected endpoint.

## Tech Stack

- **Java**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **PostgreSQL**
- **Spring Data JPA**
- **Maven**
- **Postman**

## Architecture

The application is structured around Spring Security's filter chain and JWT authentication flow.

<img width="1406" height="792" alt="image" src="https://github.com/user-attachments/assets/4c45e17c-c0f8-4dff-b1dd-eb307b7c39b7" />


### Authentication Flow

1. A user registers through the authentication endpoint.
2. The user's details are stored in PostgreSQL.
3. The user authenticates through the login endpoint.
4. The application generates a JWT.
5. The client sends the JWT with requests to protected endpoints.
6. `JwtAuthenticationFilter` intercepts the request and checks the token.
7. `JwtService` validates the JWT and retrieves the user's information.
8. The authenticated user is placed into the `SecurityContextHolder`.
9. Spring Security allows the request to reach the protected `DemoController`.
10. Requests with a missing or invalid JWT are rejected.

## Key Features

- User registration
- User authentication/login
- JWT token generation
- JWT token validation
- Custom JWT authentication filter
- Spring Security configuration
- Protected API endpoints
- `SecurityContextHolder` authentication
- PostgreSQL persistence
- API testing with Postman
- User roles and authorities

## API Endpoints

### Register

```http
POST /api/v1/auth/register
```

Creates a new user account.

### Authenticate

```http
POST /api/v1/auth/authenticate
```

Authenticates an existing user and returns a JWT token.

### Demo Controller

The `DemoController` contains a protected endpoint used to demonstrate JWT authentication.

The endpoint requires a valid JWT in the request:

```http
Authorization: Bearer <JWT_TOKEN>
```

This was tested using Postman to verify that an authenticated request can access a protected Spring Security endpoint.

<img width="635" height="541" alt="image" src="https://github.com/user-attachments/assets/788b8909-4ac3-4a18-9e7f-0869db58fe0b" />


## Testing With Postman

The complete authentication flow can be tested using Postman:

```text
Register
   ↓
Authenticate
   ↓
Receive JWT
   ↓
Add JWT to Authorization header
   ↓
Request protected endpoint
   ↓
JwtAuthenticationFilter
   ↓
Validate JWT
   ↓
SecurityContextHolder
   ↓
DemoController
```

A request without a valid token is rejected by Spring Security.

<img width="635" height="541" alt="image" src="https://github.com/user-attachments/assets/29163169-16ed-43eb-931a-fe6a7324b6e7" />


## Registering a user

<img width="635" height="541" alt="image" src="https://github.com/user-attachments/assets/a3fdba04-c8bf-44d2-93e0-65fb040ebd7b" />

## Authenticating a registered user

<img width="635" height="541" alt="image" src="https://github.com/user-attachments/assets/a6a7833a-bd62-4925-a089-d6f031fd6a29" />


## Spring Security

The main focus of this project is understanding how **Spring Security and JWT authentication work together**.

### `SecurityConfig`

Configures Spring Security and defines which endpoints require authentication.

### `JwtAuthenticationFilter`

Intercepts incoming HTTP requests and checks for a JWT in the `Authorization` header.

If a valid token is found, the filter authenticates the user and updates the `SecurityContextHolder`.

### `JwtService`

Handles JWT operations such as generating and validating tokens.

### `User`

The `User` entity implements Spring Security's `UserDetails` interface, allowing Spring Security to use the stored user information during authentication.

The user's role is converted into a Spring Security authority through `getAuthorities()`.

## Database

The application uses **PostgreSQL** with **Spring Data JPA** to persist user information.

The `User` entity contains:

- ID
- First name
- Last name
- Email
- Password
- Role

The application is configured to connect to a PostgreSQL database named `jwt_security`.

## Project Structure

```text
src/main/java/com/danielradu/security
│
├── auth
│   ├── AuthenticationController
│   ├── AuthenticationRequest
│   ├── AuthenticationResponse
│   ├── AuthenticationService
│   └── RegisterRequest
│
├── config
│   ├── ApplicationConfig
│   ├── JwtAuthenticationFilter
│   ├── JwtService
│   └── SecurityConfig
│
├── demo
│   └── DemoController
│
├── user
│   ├── Role
│   ├── User
│   └── UserRepository
│
└── SecurityApplication
```

## Running the Project

### Prerequisites

- Java
- Maven
- PostgreSQL or Docker
- Postman

### Clone the repository

```bash
git clone https://github.com/Costird21/JWT-Authentification-System
cd <project-directory>
```

### Configure PostgreSQL

Configure the PostgreSQL credentials in `application.yml`.

Example:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jwt_security
    username: <username>
    password: <password>
    driver-class-name: org.postgresql.Driver
```

### Run the application

```bash
./mvnw spring-boot:run
```

The authentication endpoints can then be tested using Postman.

## What I Learned

This project gave me practical experience with:

- JWT-based authentication
- Spring Security's authentication flow
- Configuring a `SecurityFilterChain`
- Implementing a custom JWT authentication filter
- Generating and validating JWTs
- Using `SecurityContextHolder`
- Protecting REST API endpoints
- Implementing user roles and authorities
- Connecting Spring Boot to PostgreSQL
- Using Spring Data JPA
- Testing authentication flows with Postman

## Purpose

This project was built to develop a practical understanding of **backend authentication and Spring Security**, with a particular focus on understanding what happens to an HTTP request from the moment a JWT is received to the moment an authenticated request reaches a protected controller.
