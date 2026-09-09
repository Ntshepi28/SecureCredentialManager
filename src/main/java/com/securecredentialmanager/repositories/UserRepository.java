package com.securecredentialmanager.repositories;

import com.securecredentialmanager.database.DatabaseConnection;
import com.securecredentialmanager.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserRepository {

    private final Connection connection = DatabaseConnection.getConnection();

    // Save user to database
    public boolean saveUser(User user) {

        String sql = """
                INSERT INTO users
                (username, email, password_hash, account_status, failed_login_attempts)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setString(4, user.getAccountStatus());
            statement.setInt(5, user.getFailedLoginAttempts());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Find user by username
    public User findByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                Timestamp lastLoginTimestamp = rs.getTimestamp("last_login");
                Timestamp createdAtTimestamp = rs.getTimestamp("created_at");
                Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");

                LocalDateTime lastLogin = lastLoginTimestamp != null
                        ? lastLoginTimestamp.toLocalDateTime()
                        : null;

                LocalDateTime createdAt = createdAtTimestamp != null
                        ? createdAtTimestamp.toLocalDateTime()
                        : null;

                LocalDateTime updatedAt = updatedAtTimestamp != null
                        ? updatedAtTimestamp.toLocalDateTime()
                        : null;

                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("account_status"),
                        rs.getInt("failed_login_attempts"),
                        lastLogin,
                        createdAt,
                        updatedAt
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}