# Workflow — Trello Clone

A full-stack Trello-style project management application built with Java / Spring Boot and React / TypeScript.

This has been created by AI being the tutor, not the coder so techniques in Spring can be learnt rather than generated.

> **Status: In Development** — Backend API in progress.

---

## Tech Stack

**Backend**
- Java 21
- Spring Boot 4
- Spring Security 7 + JWT authentication
- Spring Data JPA / Hibernate
- PostgreSQL

**Frontend** *(planned)*
- React + TypeScript
- Tailwind CSS
- React Query
- dnd-kit

**Infrastructure** *(planned)*
- Docker + Docker Compose

---

## Features

### Completed
- User registration and login with JWT authentication
- BCrypt password hashing
- Board management (create, read, update, delete)
- Board membership (owners and members)
- Role-based access control

### In Progress
- Columns (BoardColumns)
- Cards
- Tags
- Comments

### Planned
- Drag and drop column/card ordering
- React frontend
- Docker deployment

---

## Project Structure

```
src/main/java/uk/signalfire/workflow/
├── controller/        # REST controllers
├── dto/               # Data transfer objects
│   ├── auth/
│   ├── board/
│   ├── boardcolumn/
│   ├── card/
│   ├── comment/
│   ├── tag/
│   └── user/
├── model/             # JPA entities
│   └── enums/
├── repository/        # Spring Data repositories
├── security/          # JWT filter, config, UserDetailsService
└── service/           # Business logic
```

---

## Getting Started

### Prerequisites
- Java 21
- Maven
- PostgreSQL

### Setup

1. Clone the repository:
```bash
git clone https://github.com/your-username/workflow.git
cd workflow
```

2. Create a PostgreSQL database:
```sql
CREATE DATABASE trello_clone;
CREATE USER trello WITH PASSWORD 'secret';
GRANT ALL PRIVILEGES ON DATABASE trello_clone TO trello;
```

3. Copy the example env file and fill in your values:
```bash
cp .env.example .env
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## API Endpoints

### Auth
| Method | Endpoint | Description | Auth required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Login and receive JWT | No |

### Boards
| Method | Endpoint | Description | Auth required |
|--------|----------|-------------|---------------|
| GET | `/api/boards` | Get all boards for current user | Yes |
| POST | `/api/boards` | Create a new board | Yes |
| GET | `/api/boards/{id}` | Get a board by ID | Yes |
| PUT | `/api/boards/{id}` | Update a board | Yes (owner only) |
| DELETE | `/api/boards/{id}` | Delete a board | Yes (owner only) |

---

## Environment Variables

See `.env.example` for required environment variables.

| Variable | Description |
|----------|-------------|
| `DB_URL` | PostgreSQL connection URL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Base64-encoded JWT signing secret |
| `JWT_EXPIRATION` | JWT expiry in milliseconds (e.g. 86400000 for 24h) |

---

## License

MIT
