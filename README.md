# Doctor Appointment System - Spring Boot

A comprehensive RESTful backend for managing clinic appointments with JWT authentication and role-based access control. Built with Spring Boot, MySQL, and Springdoc OpenAPI.

## 🚀 Features

- **JWT Authentication** - Secure token-based authentication for all protected endpoints
- **Role-Based Access Control** - Three user roles with specific permissions:
  - `ADMIN` - Manages doctors, specializations, and system configuration
  - `DOCTOR` - Views and manages own profile and appointments
  - `PATIENT` - Manages personal profile and books/manages appointments
- **RESTful API Design** - Clean, organized endpoints grouped by domain
- **Interactive API Documentation** - Swagger UI with authorization persistence
- **Comprehensive Logging** - Request/response logging for debugging and monitoring

## 🛠️ Technology Stack

- Java 17+
- Spring Boot 3.x
- Spring Security with JWT
- Spring Data JPA
- MySQL Database
- Springdoc OpenAPI (Swagger)
- Maven

## 📋 Prerequisites

- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.6+
- MySQL Server 8.0+ running on port 3307
- Your favorite IDE (IntelliJ IDEA, Eclipse, or VS Code)

## ⚙️ Configuration

Create or update `src/main/resources/application.properties`:

```properties
# Server Configuration
server.port=8081
server.servlet.context-path=/das-backend

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3307/das-backend?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Logging Configuration
logging.level.com.mandeepa.das_backend=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG

# Swagger/OpenAPI Configuration
springdoc.swagger-ui.path=/swagger-ui
springdoc.swagger-ui.persist-authorization=true
springdoc.swagger-ui.try-it-out-enabled=true
springdoc.api-docs.path=/api-docs
```

## 🚦 Getting Started

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd doctor-appointment-system
```

### 2. Configure Database
Ensure MySQL is running on port 3307, or update the port in `application.properties`. The database will be created automatically on first run.

### 3. Build the Project
```bash
mvn clean install
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8081/das-backend`

### 5. Access Swagger UI
Navigate to: `http://localhost:8081/das-backend/swagger-ui`

## 📚 API Documentation

### Base URL
```
http://localhost:8081/das-backend
```

### API Endpoints

#### 🔐 Authentication (`/v1/auth`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/v1/auth/signUp` | Register new user | Public |
| POST | `/v1/auth/signIn` | Login and get JWT token | Public |

#### 👨‍⚕️ Doctors (`/api/doctors`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/doctors` | Create new doctor | ADMIN |
| GET | `/api/doctors` | List all doctors (with filters) | All |
| GET | `/api/doctors/{id}` | Get doctor by ID | All |
| PUT | `/api/doctors/{id}` | Update doctor details | ADMIN/DOCTOR |

**Query Parameters for GET /api/doctors:**
- `name` - Filter by doctor name (optional)
- `specId` - Filter by specialization ID (optional)
- `page` - Page number (default: 0)
- `size` - Page size (default: 10)

#### 🏥 Specializations (`/api/specializations`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/specializations` | Create specialization | ADMIN |
| GET | `/api/specializations` | List all specializations | All |
| PUT | `/api/specializations/{id}` | Update specialization | ADMIN |
| DELETE | `/api/specializations/{id}` | Delete specialization | ADMIN |

#### 👤 Patients (`/api/patients`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| GET | `/api/patients/me` | Get current patient profile | PATIENT |
| PUT | `/api/patients/me` | Update patient profile | PATIENT |

#### 📅 Appointments (`/api/appointments`)

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/api/appointments` | Book new appointment | PATIENT |
| GET | `/api/appointments/my` | Get user's appointments | PATIENT/DOCTOR |
| GET | `/api/appointments/{id}` | Get appointment details | PATIENT/DOCTOR |
| PUT | `/api/appointments/{id}/status` | Update appointment status | DOCTOR/ADMIN |
| DELETE | `/api/appointments/{id}` | Cancel appointment | PATIENT |

## 🔑 Authentication Flow

### 1. Sign Up

**Endpoint:** `POST /das-backend/v1/auth/signUp`

**Request Body:**
```json
{
  "firstName": "Mandeepa",
  "lastName": "De Silva",
  "username": "mandeepa@example.com",
  "password": "SecurePass@123",
  "userType": "PATIENT"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "firstName": "Mandeepa",
  "lastName": "De Silva",
  "username": "mandeepa@example.com",
  "password": "$2a$10$...",
  "phoneNumber": null,
  "address": null,
  "dob": null,
  "userType": "PATIENT",
  "createdAt": "2025-11-20T13:19:30.182Z",
  "updatedAt": "2025-11-20T13:19:30.182Z"
}
```

**Available User Types:**
- `ADMIN` - System administrator
- `DOCTOR` - Medical practitioner
- `PATIENT` - Regular patient user

### 2. Sign In

**Endpoint:** `POST /das-backend/v1/auth/signIn`

**Request Body:**
```json
{
  "username": "mandeepa@example.com",
  "password": "SecurePass@123"
}
```

**Response:** `200 OK`
```json
{
  "jwtToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTYzMjE1MDQwMCwiZXhwIjoxNjMyMTU0MDAwfQ.signature"
}
```

### 3. Using the Token

Add the token to request headers:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**In Swagger UI:**
1. Click the **Authorize** button (🔓)
2. Enter: `Bearer <your-token>`
3. Click **Authorize**
4. The token will be persisted for all subsequent requests

**Using cURL:**
```bash
curl -X GET "http://localhost:8081/das-backend/api/doctors" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

## 👥 Demo Users

Create these demo users for testing different roles:

### Admin User
```json
{
  "firstName": "Admin",
  "lastName": "User",
  "username": "admin@example.com",
  "password": "Admin@123",
  "userType": "ADMIN"
}
```

### Doctor User
```json
{
  "firstName": "Dr. Jayamini",
  "lastName": "Smith",
  "username": "doctor@example.com",
  "password": "Doctor@123",
  "userType": "DOCTOR"
}
```

### Patient User
```json
{
  "firstName": "Sahan",
  "lastName": "perera",
  "username": "sahan@example.com",
  "password": "sa@123",
  "userType": "PATIENT"
}
```

## 📝 API Usage Examples

### Example 1: Complete Authentication Flow

```bash
# 1. Sign Up
curl -X POST "http://localhost:8081/das-backend/v1/auth/signUp" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Mandeepa",
    "lastName": "De Silva",
    "username": "mandeepa@example.com",
    "password": "SecurePass@123",
    "userType": "PATIENT"
  }'

# 2. Sign In
curl -X POST "http://localhost:8081/das-backend/v1/auth/signIn" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "mandeepa@example.com",
    "password": "SecurePass@123"
  }'

# Response: {"jwtToken": "eyJhbGc..."}

# 3. Use Token for Protected Endpoints
curl -X GET "http://localhost:8081/das-backend/api/doctors" \
  -H "Authorization: Bearer eyJhbGc..."
```

### Example 2: Doctor Management (Admin Only)

```bash
# Create a new doctor (requires ADMIN role)
curl -X POST "http://localhost:8081/das-backend/api/doctors" \
  -H "Authorization: Bearer <admin-token>" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Dr. Michael",
    "lastName": "Johnson",
    "username": "dr.johnson@example.com",
    "password": "Doctor@123",
    "userType": "DOCTOR",
    "phoneNumber": "+1234567890",
    "specializationId": 1
  }'
```

### Example 3: List Doctors with Filters

```bash
# Get all doctors
curl -X GET "http://localhost:8081/das-backend/api/doctors" \
  -H "Authorization: Bearer <token>"

# Filter by name
curl -X GET "http://localhost:8081/das-backend/api/doctors?name=Johnson" \
  -H "Authorization: Bearer <token>"

# Filter by specialization
curl -X GET "http://localhost:8081/das-backend/api/doctors?specId=1" \
  -H "Authorization: Bearer <token>"

# Pagination
curl -X GET "http://localhost:8081/das-backend/api/doctors?page=0&size=5" \
  -H "Authorization: Bearer <token>"
```

## 🐛 Troubleshooting

### Common Issues

**1. Database Connection Error**
```
Error: Could not connect to database
Solution: Verify MySQL is running on port 3307 and credentials are correct
```

**2. Port Already in Use**
```
Error: Port 8081 is already in use
Solution: Change server.port in application.properties or stop the conflicting process
```

**3. JWT Token Invalid/Expired**
```
Error: 401 Unauthorized
Solution: Sign in again to get a new token. Tokens typically expire after a set duration.
```

**4. Username Already Exists**
```
Error: 409 Conflict
Solution: Use a different username/email address
```

## 📝 Development Guidelines

### Project Structure
```
src/main/java/com/mandeepa/das_backend
├── advice/                 # Global exception handling
│   └── GlobalExceptionHandler.java
├── aop/                    # AOP & cross-cutting concerns
│   └── LoggingAspect.java
├── config/                 # Core configuration
│   ├── SecurityConfig.java         # Spring Security + JWT filter chain
│   ├── JwtFilter.java              # OncePerRequestFilter reading Bearer token
│   ├── OpenApiConfig.java          # Swagger / OpenAPI configuration
│   ├── BeanConfig.java             # Shared beans (ModelMapper, etc.)
│   └── AuthDebugFilter.java        # Optional debug filter for auth context
├── constant/               # Shared enums & constants
│   ├── AppointmentStatus.java
│   └── UserType.java
├── docs/                   # Design / documentation artifacts
│   └── domain-model-uml.puml      # ERD / domain model (PlantUML)
├── dto/                    # Data Transfer Objects (no entities exposed)
│   ├── appointment/
│   │   ├── AppointmentCreateRequest.java
│   │   ├── AppointmentResponse.java
│   │   └── AppointmentStatusUpdateRequest.java
│   ├── doctor/
│   │   ├── DoctorCreateRequest.java
│   │   ├── DoctorPublicResponse.java
│   │   └── DoctorUpdateRequest.java
│   ├── patient/
│   │   ├── PatientMeResponse.java
│   │   └── PatientUpdateRequest.java
│   ├── prescription/
│   │   ├── PrescriptionCreateRequest.java
│   │   └── PrescriptionResponse.java
│   ├── specialization/
│   │   ├── SpecializationCreateRequest.java
│   │   ├── SpecializationUpdateRequest.java
│   │   └── SpecializationResponse.java
│   └── UserDto.java
├── entity/                 # JPA entities and relationships
│   ├── UserEntity.java
│   ├── PatientEntity.java
│   ├── DoctorEntity.java
│   ├── SpecializationEntity.java
│   ├── AppointmentEntity.java
│   └── PrescriptionEntity.java
├── exception/              # Custom exceptions & error models
│   ├── DuplicateFoundException.java
│   ├── ResourceNotFoundException.java
│   ├── UnAuthorizedException.java
│   └── ErrorResponse.java
├── repository/             # Spring Data JPA repositories
│   ├── UserRepository.java
│   ├── PatientRepository.java
│   ├── DoctorRepository.java
│   ├── SpecializationRepository.java
│   ├── AppointmentRepository.java
│   └── PrescriptionRepository.java
├── rest/                   # HTTP API layer
│   ├── api/                # Interface contracts
│   │   ├── LoginApi.java
│   │   ├── DoctorApi.java
│   │   ├── PatientApi.java
│   │   ├── AppointmentApi.java
│   │   ├── PrescriptionApi.java
│   │   └── SpecializationApi.java
│   └── controller/         # REST controllers implementing APIs
│       ├── LoginController.java
│       ├── DoctorController.java
│       ├── PatientController.java
│       ├── AppointmentController.java
│       ├── PrescriptionController.java
│       └── SpecializationController.java
├── server/                 # Security, auth, and supporting services
│   ├── JwtToken/
│   │   └── JwtSignINResponse.java
│   ├── user/
│   │   ├── UserSignupRequest.java
│   │   ├── UserSignInRequest.java
│   │   └── UserResponse.java
│   ├── JwtService.java
│   ├── MyUserDetailsService.java
│   └── UserPrincipal.java
├── service/            # Domain services (interfaces + impl)
│   ├── auth/
│   │   ├── LoginService.java
│   │   └── LoginServiceImpl.java
│   ├── doctor/
│   │   ├── DoctorService.java
│   │   └── DoctorServiceImpl.java
│   ├── patient/
│   │   ├── PatientService.java
│   │   └── PatientServiceImpl.java
│   ├── appointment/
│   │   ├── AppointmentService.java
│   │   └── AppointmentServiceImpl.java
│   ├── prescription/
│   │   ├── PrescriptionService.java
│   │   └── PrescriptionServiceImpl.java
│   └── specialization/
│       ├── SpecializationService.java
│       └── SpecializationServiceImpl.java
└── DasBackendApplication.java      # Spring Boot entry point

src/main/resources
├── application.properties          # DB, JWT, Swagger, profiles, etc.
```

### Adding New Features

1. **Create Entity** (`entity/` package)
2. **Create Repository** interface in `repository/`
3. **Create DTOs** in `dto/request/` and `dto/response/`
4. **Implement Service** in `service/` and `service/impl/`
5. **Create Controller** in `controller/`
6. **Add Swagger Annotations** for API documentation
7. **Update Security Config** if new endpoints need specific roles

## 🔒 Security Best Practices

- Passwords are encrypted using BCrypt
- JWT tokens are stateless and contain user information
- Role-based access control enforced at controller level
- Input validation on all request DTOs
- SQL injection prevention through JPA/Hibernate
- CORS configuration for cross-origin requests

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📧 Contact

Name - Mandeepa De Silva

Project Link: [https://github.com/Mandeepa-De-Silva/doctor-appointment-system](https://github.com/Mandeepa-De-Silva/doctor-appointment-system)

---
