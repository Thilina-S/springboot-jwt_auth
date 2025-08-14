Here's an enhanced **README.md** section specifically addressing the IntelliJ IDEA backend implementation:

---

# 🔧 Backend Setup with IntelliJ IDEA

## 🛠️ Development Environment

The Spring Boot backend was developed using **IntelliJ IDEA Ultimate/Community Edition** with these configurations:

### Prerequisites
- IntelliJ IDEA 2022.3+ (Recommended)
- Java 17 JDK (Amazon Corretto or OpenJDK)
- Maven 3.8.6+
- MySQL 8.0+ / PostgreSQL 14+ (or your preferred database)

## 🚀 IntelliJ Setup Guide

1. **Import Project**:
   - Open IntelliJ → File → Open
   - Select the `backend/pom.xml` file
   - Choose "Open as Project"

2. **Configure SDK**:
   - File → Project Structure → Project Settings → Project
   - Set Project SDK to Java 21
   - Set Project language level to 17

3. **Database Configuration**:
   ```properties
   # src/main/resources/application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/auth_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **Run Configuration**:
   - Create new Maven configuration:
     - Command line: `spring-boot:run`
     - Working directory: `$MODULE_DIR$`
   - Or run directly:
     - Right-click `AuthApplication.java` → Run

## 🧩 IntelliJ-Specific Features Used

- **Lombok Plugin** (Must enable):
  - Settings → Build → Compiler → Annotation Processors
  - Check "Enable annotation processing"

- **Database Tools**:
  - Integrated Database view for schema management
  - SQL query console for testing

- **Spring Boot Dashboard**:
  - View active profiles
  - Monitor actuator endpoints
  - Manage running instances

- **HTTP Client**:
  - Test API endpoints directly from IDEA
  - Sample `.http` files included in project

## 🧪 Testing in IntelliJ

1. **Run Unit Tests**:
   - Right-click `src/test/java` → Run All Tests
   - Or use Maven: `mvn test`

2. **API Testing**:
   ```http
   ### Register User
   POST http://localhost:8080/api/auth/register
   Content-Type: application/json

   {
     "username": "testuser",
     "email": "test@example.com",
     "password": "securePassword123!"
   }
   ```

## 🔍 Debugging Tips

1. **Breakpoints**:
   - Set breakpoints in:
     - `JwtAuthenticationFilter`
     - `AuthController`
     - `UserDetailsServiceImpl`

2. **Evaluate Expressions**:
   - Use IDEA's debugger to inspect:
     - JWT token generation
     - Password hashing process
     - Security context holder

3. **Database Inspection**:
   - Use Database view to verify:
     - User table structure
     - Password hash storage
     - Token blacklisting (if implemented)

## 🏗️ Project Structure in IntelliJ

```
backend (module)
└── src
    ├── main
    │   ├── java/com/auth
    │   │   ├── config/       # Spring Security config
    │   │   ├── controller/   # REST controllers
    │   │   ├── dto/          # Request/response objects
    │   │   ├── exception/    # Custom exceptions
    │   │   ├── model/        # JPA entities
    │   │   ├── repository/   # Spring Data interfaces
    │   │   ├── security/     # JWT components
    │   │   ├── service/      # Business logic
    │   │   └── AuthApplication.java
    │   └── resources
    │       ├── application.properties
    │       └── application-dev.properties
    └── test                  # Unit tests
```

## 💡 Recommended Plugins

1. **Lombok Plugin** (Essential)
2. **Spring Boot Assistant**
3. **Database Tools and SQL**
4. **Rainbow Brackets** (For better code readability)
5. **GitToolBox** (Enhanced Git integration)

---

