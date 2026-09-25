#  Secure Credential Manager

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-GUI-blue?style=for-the-badge&logo=openjdk)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-336791?style=for-the-badge&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)
![Security](https://img.shields.io/badge/Security-AES--GCM%20%7C%20BCrypt-green?style=for-the-badge)

A robust, lightweight desktop credential management application built with **JavaFX**, **JDBC**, and relational databases (**PostgreSQL / MySQL**). Secure Credential Manager empowers users to safely store encrypted credentials, organize accounts into custom categories, test password resilience, generate cryptographically secure passwords, and track account security through automated audit logging.

---

##  Key Features

###  Core Security & Cryptography
* **AES-GCM Encryption:** Vault credentials (passwords) are encrypted using AES in Galois/Counter Mode before DB persistence.
* **BCrypt Password Hashing:** User master passwords are salted and hashed using industry-standard BCrypt.
* **Account Lockout Policy:** Protects accounts against brute-force attacks by locking access after **5 consecutive failed attempts**.
* **Audit Logging:** Automatically tracks critical security events (`LOGIN_SUCCESS`, `LOGIN_FAILED`, `ACCOUNT_LOCKED`, `PASSWORD_GENERATED`, etc.) into an immutable database log.

###  Credential & Category Management
* **Interactive Dashboard:** Seamlessly create, view, copy, and delete vault entries through a real-time `TableView`.
* **Category Organization:** Create custom categories (*Work*, *Finance*, *Personal*) and assign them to credentials using dynamic UI dropdowns.
* **Clipboard Integration:** Securely copy decrypted credentials or newly generated passwords with a single click.

###  Security Center
* **Password Strength Evaluator:** Real-time scoring ($0-6$) and dynamic strength feedback on user passwords.
* **Cryptographic Generator:** Configurable, multi-character generator powered by Java’s `SecureRandom` ($8-32$ characters).

---

##  System Architecture

The project strictly enforces the **MVC (Model-View-Controller)** design pattern with dedicated service and repository layers:

┌─────────────────────────────────────────────────────────┐
│                      JavaFX Views                       │
│    (CredentialView, CategoryView, SecurityView, etc.)   │
└────────────────────────────┬────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────────┐
│                       Controllers                       │
│ (CredentialController, CategoryController, Security...) │
└────────────────────────────┬────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────────┐
│                        Services                         │
│ (AuthenticationService, CategoryService, SecurityLog...)│
└────────────────────────────┬────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────────┐
│                      Repositories                       │
│(CredentialRepository, CategoryRepository, AuditLogs...) │
└────────────────────────────┬────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────────┐
│                  Relational Database                    │
│                 (PostgreSQL / MySQL)                    │
└─────────────────────────────────────────────────────────┘

---

## 🗄️ Database Schema

Execute the SQL initialization script below to prepare your database environment:

```sql
-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    account_status VARCHAR(20) DEFAULT 'ACTIVE',
    failed_login_attempts INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Categories Table
CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Credentials Table
CREATE TABLE IF NOT EXISTS credentials (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    category_id INT REFERENCES categories(id) ON DELETE SET NULL,
    service_name VARCHAR(100) NOT NULL,
    website VARCHAR(255),
    login_username VARCHAR(100) NOT NULL,
    encrypted_password TEXT NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Audit Logs Table
CREATE TABLE IF NOT EXISTS audit_logs (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    action VARCHAR(50) NOT NULL,
    description TEXT,
    ip_address VARCHAR(45),
    device_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

3. Build & Run

Execute the application via Maven:
mvn clean compile javafx:run

src/main/java/com/securecredentialmanager/
├── controllers/       # Bridges JavaFX Views and Service business logic
├── database/          # Database connection provider and configuration
├── enums/             # Strongly-typed AuditAction enumeration definitions
├── models/            # Entity models (User, Credential, Category, AuditLogs)
├── repositories/      # JDBC DAO pattern for SQL persistence
├── services/          # Core security, encryption, and authentication engines
└── ui/                # JavaFX layout containers and views
WTC-SQMBMM7G
