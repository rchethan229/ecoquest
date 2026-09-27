# EcoQuest - Sustainability Tracking System


## Architecture

- Eureka Server - `8761`
- API Gateway - `8080`
- User/Auth/Profile Service (`profile-service`) - `8081`
- Effort Service - `8082`
- Reward Service - `8083`
- React + Vite frontend - `5173`

### Services

**Profile/Auth Service**
- Sign up
- BCrypt password hashing
- Login
- JWT creation
- USER/ADMIN roles
- User profile storage
- Unique email validation

**Effort Service**
- Logs sustainable activities
- Calculates CO2 saved
- Calculates EcoPoints
- Gets the logged-in user ID from JWT instead of trusting a browser-supplied userId
- Calls Reward Service using OpenFeign

**Reward Service**
- Maintains total EcoPoints
- Calculates level
- Provides user reward data
- Admin can view all rewards

## User flow

1. Open the frontend.
2. Click **Sign Up**.
3. Enter name, email, password and location.
4. The backend automatically creates the user profile at signup time.
5. The backend rejects an email that is already registered.
6. Sign in using the same email and password.
7. Login returns a JWT containing user ID and role.
8. The frontend stores the JWT and automatically sends it with Axios requests.
9. A normal USER is immediately sent to **Log Effort**.
10. When the user logs an effort, the browser does **not** send userId. The backend gets userId from the JWT.
11. Effort Service calculates CO2 saved and EcoPoints.
12. Effort Service calls Reward Service through OpenFeign.
13. The user can open My Dashboard to see profile, efforts, CO2 saved, EcoPoints and level.


## Email uniqueness

Email uniqueness is protected twice:

1. Java checks `existsByEmail(...)` before creating the account.
2. PostgreSQL has a unique constraint on the `users.email` column.

The application normalizes public signup emails to lowercase. Therefore `User@Example.com` and `user@example.com` are treated as the same signup email by the application.

If two requests arrive at the same time, the database unique constraint is the final protection.

## Password security

Passwords are **hashed with BCrypt**, not encrypted.

The database never stores the original password. Login uses BCrypt `matches()` to compare the entered password with the stored hash.

## Roles

Public signup always creates `USER`.

Do not add a `role` field to the frontend signup request. An ADMIN must be created manually by changing an existing user's role in PostgreSQL.

### Make an account ADMIN

1. Sign up normally using the frontend, for example `admin@example.com`.
2. Open PostgreSQL / pgAdmin and select database `profiledb`.
3. Run:

```sql
UPDATE users
SET role = 'ADMIN'
WHERE email = 'admin@example.com';
```

4. Sign out and sign in again so a new JWT contains `ADMIN`.
5. The admin is sent to `/admin` and can view all profiles, efforts and rewards.

## PostgreSQL databases

Create these databases before starting the services:

- `profiledb`
- `effortdb`
- `rewarddb`

The project currently uses:

- PostgreSQL username: `postgres`
- PostgreSQL password: `uday`

If your PostgreSQL password is different, change it in the three `application.properties` files.

### Important when upgrading from the old ZIP

The old `profiledb.users` table did not contain the password and role fields required by JWT authentication. For a clean first run of this modified project, it is easiest to remove/recreate the old `profiledb` database and let Hibernate create the new `users` table.

Do the same only if you want a completely clean test database for the effort/reward data.

## Start order

Start manually, no Docker/WildFly is required.

1. `eureka-server`
2. `profile-service`
3. `effort-service`
4. `reward-service`
5. `api-gateway`
6. React frontend

## Backend commands

From each service folder:

```bash
mvn spring-boot:run
```

Or run each `main` class from IntelliJ.

## Frontend commands

```bash
cd eco-tracker-frontend
npm install
npm run dev
```

Open `http://localhost:5173`.

## Main API endpoints

### Public

```text
POST /auth/signup
POST /auth/login
```

### Logged-in user

```text
GET  /profiles/me
POST /efforts
GET  /efforts/me
GET  /rewards/me
```

### Admin

```text
GET /profiles
GET /efforts
GET /rewards
```

## JWT

All secured services use the same JWT secret in their local `application.properties` files. For a real deployment, move this secret to an environment variable or secret manager.

The JWT contains:

- userId
- role
- name
- email as the token subject

## CORS

CORS is configured only in the API Gateway. The individual services do not add another `Access-Control-Allow-Origin` header. This avoids the previous "multiple allow origin values" problem.

## Project folders

```text
eco-tracker-microservices/
├── eureka-server/
├── api-gateway/
├── profile-service/
├── effort-service/
├── reward-service/
├── eco-tracker-frontend/
└── admin-setup.sql
```

## 🛠️ Tools & Technologies

![Tools and Technologies]("\images\Tools and Technologies.png")

## System Architechture
![System Architechture]("\images\1_system_architecture.png")

## Project Workflow
![Project Workflow]("\images\2_flow_of_operation.png")


## Sign In 
![Sign In ]("\images\Sign in page.png")

## Sign Up
![Sign Up ]("\images\Sign Up page.png")

## Logging Efforts
![Logging Efforts ]("\images\Log Efforts page.png")


## User DashBoard
![User DashBoard]("\images\User dashboard.png")

## Admin DashBoard
![Admin DashBoard]("\images\Admin dashboard.png")


