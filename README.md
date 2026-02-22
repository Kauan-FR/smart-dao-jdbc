# Smart DAO JDBC

A data management system built with **pure JDBC** and the **DAO + Factory Pattern**, connected to PostgreSQL. This project demonstrates advanced JDBC techniques, connection pooling, database migrations, and REST API development — all without ORM frameworks like JPA/Hibernate.

> 🇧🇷 **[Versão em Português](#versão-em-português)**

---

## Table of Contents

- [About](#about)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Running with Docker](#running-with-docker)
- [Running Locally](#running-locally)
- [Testing](#testing)
- [Swagger Documentation](#swagger-documentation)
- [Project Structure](#project-structure)
- [Versão em Português](#versão-em-português)

---

## About

Smart DAO JDBC is a portfolio project that showcases how to build a professional, production-ready Java application using **raw JDBC** instead of ORM frameworks. The project focuses on demonstrating:

- **DAO Pattern** with Factory Pattern for clean data access abstraction
- **HikariCP** connection pooling for high-performance database access
- **Flyway** for versioned database migrations
- **JUnit 5** integration tests with real database operations
- **REST API** with proper exception handling and HTTP status codes
- **Swagger/OpenAPI** for interactive API documentation
- **Docker** for one-command deployment

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 (LTS) | Core language |
| Spring Boot | 4.0.2 | REST API framework |
| PostgreSQL | 17+ | Relational database |
| HikariCP | 6.2.1 | Connection pool |
| Flyway | 11.x | Database migrations |
| JUnit 5 | - | Integration testing |
| Springdoc OpenAPI | 2.8.6 | Swagger UI |
| Maven | - | Build tool |
| Docker | - | Containerization |

---

## Architecture

The project follows a layered architecture with clear separation of concerns:

```
Controller Layer  →  REST endpoints (Spring Boot)
       ↓
    DAO Layer     →  Data access interfaces + JDBC implementations
       ↓
  Database Layer  →  PostgreSQL with HikariCP connection pool
```

Key architectural decisions:
- **Pure JDBC** — no JPA/Hibernate, demonstrating manual SQL and PreparedStatement usage
- **DaoFactory** — centralized DAO instantiation, injected directly into controllers
- **Custom exceptions** — granular error handling mapped to proper HTTP status codes
- **Single connection pool** — shared between DAOs and Flyway via the `DB` utility class

---

## API Endpoints

### Departments

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/departments` | List all departments |
| `GET` | `/api/departments?page=1&size=3` | List with pagination |
| `GET` | `/api/departments/{id}` | Find by ID |
| `POST` | `/api/departments` | Create new department |
| `PUT` | `/api/departments/{id}` | Update department |
| `DELETE` | `/api/departments/{id}` | Delete department |

### Sellers

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/sellers` | List all sellers |
| `GET` | `/api/sellers?page=1&size=3` | List with pagination |
| `GET` | `/api/sellers/{id}` | Find by ID |
| `GET` | `/api/sellers/email/{email}` | Find by email |
| `GET` | `/api/sellers/name/{name}` | Search by name (case-insensitive) |
| `GET` | `/api/sellers/department/{departmentId}` | Find by department |
| `GET` | `/api/sellers/birth-month/{month}` | Find by birth month (1-12) |
| `POST` | `/api/sellers` | Create new seller |
| `PUT` | `/api/sellers/{id}` | Update seller |
| `DELETE` | `/api/sellers/{id}` | Delete seller |

### Error Responses

All errors follow a consistent JSON structure:

```json
{
    "timestamp": "2026-02-22T16:00:00Z",
    "status": 404,
    "error": "Resource not found",
    "message": "Seller not found. Id: 99",
    "path": "/api/sellers/99"
}
```

| Status | Exception | Description |
|---|---|---|
| `400` | `IllegalArgumentException` | Invalid input (null name, invalid email format) |
| `404` | `EntityNotFoundException` | Resource not found |
| `409` | `DuplicateEntryException` | Unique constraint violation (duplicate email) |
| `409` | `DbIntegrityException` | Referential integrity violation |
| `500` | `DbException` | General database error |

---

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+
- PostgreSQL 17+ **OR** Docker

---

## Running with Docker

The fastest way to run the project. No Java or PostgreSQL installation required.

**1. Clone the repository:**
```bash
git clone https://github.com/Kauan-FR/smart-dao-jdbc.git
cd smart-dao-jdbc
```

**2. Create the `.env` file from the example:**
```bash
cp .env.example .env
```

**3. Edit the `.env` file with your credentials:**
```env
DB_NAME=smartdaodb
DB_USER=your_username
DB_PASSWORD=your_password
```

**4. Start the application:**
```bash
docker compose up --build
```

**5. Access the API:**
- API: http://localhost:8080/api/departments
- Swagger UI: http://localhost:8080/swagger-ui.html

**To stop:**
```bash
docker compose down
```

---

## Running Locally

**1. Clone the repository:**
```bash
git clone https://github.com/Kauan-FR/smart-dao-jdbc.git
cd smart-dao-jdbc
```

**2. Create the PostgreSQL database:**
```bash
createdb smartdaodb
```

**3. Update `src/main/resources/db.properties` with your credentials:**
```properties
user=your_username
password=your_password
dburl=jdbc:postgresql://localhost:5432/smartdaodb
```

**4. Run the application:**
```bash
mvn spring-boot:run
```

Flyway will automatically create the tables and insert seed data on first run.

**5. Access the API:**
- API: http://localhost:8080/api/departments
- Swagger UI: http://localhost:8080/swagger-ui.html

---

## Testing

The project includes **19 integration tests** (12 for Seller, 7 for Department) that run against a real PostgreSQL database.

```bash
mvn test
```

Tests cover all DAO operations including CRUD, pagination, search methods, and exception scenarios.

An **Insomnia collection** is also available at `src/main/resources/` for manual API testing. Import the YAML file into Insomnia to test all endpoints.

---

## Swagger Documentation

Interactive API documentation is available at:

```
http://localhost:8080/swagger-ui.html
```

---

## Project Structure

```
com.kauanferreira.smartdaojdbc
│
├── controller
│   ├── SellerController.java          # REST endpoints for Seller
│   ├── DepartmentController.java      # REST endpoints for Department
│   ├── GlobalExceptionHandler.java    # Centralized error handling
│   └── StandardError.java             # Error response body
│
├── dao
│   ├── SellerDao.java                 # Seller DAO interface
│   ├── DepartmentDao.java             # Department DAO interface
│   ├── DaoFactory.java                # Factory for DAO instances
│   └── impl
│       ├── SellerDaoJDBC.java         # JDBC implementation (Seller)
│       └── DepartmentDaoJDBC.java     # JDBC implementation (Department)
│
├── entity
│   ├── Seller.java                    # Seller entity
│   └── Department.java                # Department entity
│
├── exception
│   ├── DbException.java               # General database error
│   ├── DbIntegrityException.java      # Referential integrity violation
│   ├── EntityNotFoundException.java   # Entity not found
│   └── DuplicateEntryException.java   # Unique constraint violation
│
├── DB.java                            # HikariCP connection pool manager
├── DataSourceConfig.java              # Spring + Flyway integration
└── SmartdaojdbcApplication.java       # Spring Boot entry point

src/main/resources/
├── application.properties             # Spring Boot + Swagger config
├── db.properties                      # HikariCP pool settings
└── db/migration/
    ├── V1__create_tables.sql          # Table creation
    └── V2__seed_data.sql              # Sample data

src/test/java/
└── dao/impl/
    ├── SellerDaoJDBCTest.java         # 12 integration tests
    └── DepartmentDaoJDBCTest.java     # 7 integration tests
```

---

## Database Schema

```sql
CREATE TABLE department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL
);

CREATE TABLE seller (
    id SERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    birthdate DATE NOT NULL,
    basesalary DOUBLE PRECISION NOT NULL,
    departmentid INTEGER NOT NULL,
    FOREIGN KEY (departmentid) REFERENCES department (id)
);
```

---

## Author

**Kauan Santos Ferreira**
- GitHub: [Kauan-FR](https://github.com/Kauan-FR)
- Email: kauanferreira3011@gmail.com

---

---

# Versão em Português

## Sobre

Smart DAO JDBC é um projeto de portfólio que demonstra como construir uma aplicação Java profissional utilizando **JDBC puro** ao invés de frameworks ORM. O projeto foca em demonstrar:

- **Padrão DAO** com Factory Pattern para abstração limpa de acesso a dados
- **HikariCP** para pool de conexões de alta performance
- **Flyway** para migrações versionadas de banco de dados
- **JUnit 5** com testes de integração usando banco de dados real
- **API REST** com tratamento adequado de exceções e códigos HTTP
- **Swagger/OpenAPI** para documentação interativa da API
- **Docker** para deploy com um único comando

## Como Executar com Docker

**1. Clone o repositório:**
```bash
git clone https://github.com/Kauan-FR/smart-dao-jdbc.git
cd smart-dao-jdbc
```

**2. Crie o arquivo `.env` a partir do exemplo:**
```bash
cp .env.example .env
```

**3. Edite o `.env` com suas credenciais:**
```env
DB_NAME=smartdaodb
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
```

**4. Inicie a aplicação:**
```bash
docker compose up --build
```

**5. Acesse a API:**
- API: http://localhost:8080/api/departments
- Swagger UI: http://localhost:8080/swagger-ui.html

## Como Executar Localmente

**1. Clone o repositório:**
```bash
git clone https://github.com/Kauan-FR/smart-dao-jdbc.git
cd smart-dao-jdbc
```

**2. Crie o banco PostgreSQL:**
```bash
createdb smartdaodb
```

**3. Atualize `src/main/resources/db.properties` com suas credenciais:**
```properties
user=seu_usuario
password=sua_senha
dburl=jdbc:postgresql://localhost:5432/smartdaodb
```

**4. Execute a aplicação:**
```bash
mvn spring-boot:run
```

O Flyway criará as tabelas e inserirá dados de exemplo automaticamente na primeira execução.

## Endpoints da API

### Departamentos

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/departments` | Listar todos |
| `GET` | `/api/departments?page=1&size=3` | Listar com paginação |
| `GET` | `/api/departments/{id}` | Buscar por ID |
| `POST` | `/api/departments` | Criar departamento |
| `PUT` | `/api/departments/{id}` | Atualizar departamento |
| `DELETE` | `/api/departments/{id}` | Deletar departamento |

### Vendedores

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/sellers` | Listar todos |
| `GET` | `/api/sellers?page=1&size=3` | Listar com paginação |
| `GET` | `/api/sellers/{id}` | Buscar por ID |
| `GET` | `/api/sellers/email/{email}` | Buscar por email |
| `GET` | `/api/sellers/name/{name}` | Buscar por nome (case-insensitive) |
| `GET` | `/api/sellers/department/{departmentId}` | Buscar por departamento |
| `GET` | `/api/sellers/birth-month/{month}` | Buscar por mês de nascimento (1-12) |
| `POST` | `/api/sellers` | Criar vendedor |
| `PUT` | `/api/sellers/{id}` | Atualizar vendedor |
| `DELETE` | `/api/sellers/{id}` | Deletar vendedor |

## Testes

O projeto inclui **19 testes de integração** (12 para Seller, 7 para Department) que rodam contra um banco PostgreSQL real.

```bash
mvn test
```

Uma **collection do Insomnia** também está disponível em `src/main/resources/` para testes manuais da API.

## Autor

**Kauan Santos Ferreira**
- GitHub: [Kauan-FR](https://github.com/Kauan-FR)
- Email: kauanferreira3011@gmail.com