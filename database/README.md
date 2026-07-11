# Database Schema

## Overview

This folder contains the database schema for the **Secure Credential Manager** project. Each SQL file creates a specific table that is required for the application. The files are numbered so they can be executed in order because some tables depend on others through foreign key relationships.

The goal of this database is to provide a secure and organized way to store user information, credentials, application settings, and security-related records.

---

## Schema Files

### 001_create_users.sql

This file creates the **users** table.

The users table stores the application's registered users. It contains login information such as the username, email address, password hash, account status, failed login attempts, and timestamps.

This is the main table in the database because most other tables are linked to it.

**Why it is important**

* Stores user accounts
* Supports secure authentication
* Tracks account status and login attempts
* Acts as the parent table for several other tables

---

### 002_create_categories.sql

This file creates the **categories** table.

Categories allow users to organise their saved credentials into groups such as Banking, Social Media, Email, or Work.

Each category belongs to a specific user.

**Why it is important**

* Keeps credentials organised
* Makes searching and filtering easier
* Allows each user to create their own categories

---

### 003_create_credentials.sql

This file creates the **credentials** table.

This is the core table of the application. It stores the credentials that users save, including the service name, website, username, encrypted password, and optional notes.

Each credential belongs to one user and can optionally belong to a category.

**Why it is important**

* Stores all saved credentials
* Supports encrypted password storage
* Links credentials to both users and categories

---

### 004_create_password_history.sql

This file creates the **password_history** table.

Whenever a password is updated, the previous encrypted password can be stored in this table instead of being lost.

**Why it is important**

* Keeps a history of password changes
* Makes future password history features possible
* Helps with auditing and account management

---

### 005_create_audit_logs.sql

This file creates the **audit_logs** table.

It records important actions performed by users, such as logging in, changing passwords, creating credentials, or deleting information.

**Why it is important**

* Improves application security
* Helps monitor user activity
* Makes troubleshooting easier
* Provides an audit trail

---

### 006_create_user_settings.sql

This file creates the **user_settings** table.

It stores application preferences for each user, such as the selected theme, auto-lock timeout, password generator settings, and other personal preferences.

Each user has only one settings record.

**Why it is important**

* Stores user preferences separately from account information
* Makes the application easier to customise
* Keeps the database well organised

---

### 007_create_backup_history.sql

This file creates the **backup_history** table.

It records information about backups created by the application, including the backup name, file location, size, status, and creation date.

**Why it is important**

* Keeps track of all backups
* Helps users verify successful backups
* Supports future restore functionality

---

### 008_create_sessions.sql

This file creates the **sessions** table.

It stores login session information such as the session token, login time, device information, IP address, and session expiry.

**Why it is important**

* Supports secure session management
* Makes automatic logout possible
* Allows future support for multiple devices
* Improves application security

---

## Database Design

The database follows a relational design where the **users** table is the central table. The remaining tables are connected through foreign keys to maintain data integrity and reduce duplicated information.

This structure keeps the database organised, scalable, and easier to maintain as new features are added.

---

## Notes

The schema was designed with security in mind. Passwords are never stored as plain text. User account passwords are stored as hashes, while saved account credentials will be encrypted before being written to the database.

Each table has a single responsibility, making the database easier to understand, test, and maintain throughout the development of the project.
