Secure Credential Manager

A lightweight, secure desktop credential management application built with JavaFX, JDBC, and a relational database (PostgreSQL / MySQL). Secure Credential Manager allows users to safely store encrypted passwords, organize credentials into categories, assess password strengths, generate cryptographically secure passwords, and track user actions through a comprehensive audit logging system.
Key Features

    Encrypted Password Storage: Sensitive credentials (passwords) are encrypted using AES-GCM prior to database insertion.

    Credential Management (CRUD): Add, view, copy, and delete credentials seamlessly via an interactive JavaFX TableView.

    Category Management: Create, list, and delete custom categories (e.g., Work, Finance, Personal) to organize your credentials.

    Security Center:

        Password Strength Checker: Analyzes user inputs and provides dynamic feedback and scoring (0−6).

        Secure Password Generator: Generates cryptographically strong, customizable-length passwords utilizing SecureRandom.

        Clipboard Integration: Easily copy generated or decrypted passwords with a single click.

    Authentication & Security:

        Password hashing powered by BCrypt.

        Account Lockout Policy: Protects accounts by locking them after 5 consecutive failed login attempts.

    Audit Logging: Automatically records user activities (logins, failed attempts, account lockouts, password generation, category management) into a dedicated database table (audit_logs) powered by an AuditAction enum framework.

Architecture Overview

The application follows a clean MVC (Model-View-Controller) pattern with a dedicated Service and Repository layer:

UI (JavaFX Views)
  └── Controllers (CredentialController, CategoryController, SecurityController, etc.)
       └── Services (AuthenticationService, CategoryService, PasswordGeneratorService, etc.)
            └── Repositories (CredentialRepository, CategoryRepository, AuditLogsRepository, etc.)
                 └── Database Connection (JDBC / PostgreSQL or MySQL)

Tech Stack & Prerequisites

    Language: Java 21+

    UI Framework: JavaFX

    Build Tool: Maven

    Database: PostgreSQL / MySQL

    Security & Libraries:

        Java Cryptography Architecture (AES-GCM, SecureRandom)

        jBCrypt (Password Hashing)

Database Schema Setup

Before running the application, ensure your target PostgreSQL/MySQL database is active and execute the schema initialization script below:
SQL

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

Getting Started
1. Clone the Repository
Bash

git clone https://github.com/your-username/SecureCredentialManager.git
cd SecureCredentialManager

2. Configure Database Credentials

Ensure your database connection details inside src/main/java/com/securecredentialmanager/database/DatabaseConnection.java match your local environment configuration (Database URL, Username, Password).
3. Build & Run

Compile the project and run the JavaFX application using Maven:
Bash

mvn clean compile javafx:run

Project Structure

src/main/java/com/securecredentialmanager/
├── controllers/          # Handles UI events and delegates business operations
├── database/             # Database connection setup
├── enums/                # AuditAction enums for system event tracking
├── models/               # Data Transfer Objects (User, Credential, Category, AuditLogs)
├── repositories/         # Database persistence layer (JDBC Queries)
├── services/             # Core business logic (Security, Encryption, Auth)
└── ui/                   # JavaFX Views and Layout Managers

WTC-SQMBMM7G
