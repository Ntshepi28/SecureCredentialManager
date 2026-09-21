package com.securecredentialmanager.repositories;

import com.securecredentialmanager.database.DatabaseConnection;
import com.securecredentialmanager.models.Sessions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class SessionsRepository {
    private final Connection connection = DatabaseConnection.getConnection();

    public boolean saveSession(Sessions sessions){
        String sql = """
                INSERT INTO sessions
                (user_id, session_token, device_name, ip_address, expires_at, is_active)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, sessions.getUserId());
            statement.setString(2, sessions.getSessionToken());
            statement.setString(3, sessions.getDeviceName());
            statement.setString(4, sessions.getIpAddress());
            statement.setTimestamp(5, Timestamp.valueOf(sessions.getExpiresAt()));
            statement.setBoolean(6, sessions.isActive());

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Sessions findByToken(String sessionToken){
        String sql = """
                SELECT * FROM sessions
                WHERE session_token = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, sessionToken);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapSession(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Sessions findById(long id){
        String sql = """
                SELECT * FROM sessions
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapSession(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateActivity(String sessionToken, LocalDateTime lastActivity){
        String sql = """
                UPDATE sessions
                SET last_activity = ?
                WHERE session_token = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setTimestamp(1, Timestamp.valueOf(lastActivity));
            statement.setString(2, sessionToken);

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSession(String sessionToken){
        String sql = """
                DELETE FROM sessions
                WHERE session_token = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, sessionToken);

            return statement.executeUpdate() > 0;

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private Sessions mapSession(ResultSet resultSet) throws Exception{
        Timestamp loginTimeTimestamp = resultSet.getTimestamp("login_time");
        Timestamp lastActivityTimestamp = resultSet.getTimestamp("last_activity");
        Timestamp expiresAtTimestamp = resultSet.getTimestamp("expires_at");

        LocalDateTime loginTime = loginTimeTimestamp != null
                ? loginTimeTimestamp.toLocalDateTime()
                : null;

        LocalDateTime lastActivity = lastActivityTimestamp != null
                ? lastActivityTimestamp.toLocalDateTime()
                : null;

        LocalDateTime expiresAt = expiresAtTimestamp != null
                ? expiresAtTimestamp.toLocalDateTime()
                : null;

        return new Sessions(
                resultSet.getLong("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("session_token"),
                resultSet.getString("device_name"),
                resultSet.getString("ip_address"),
                loginTime,
                lastActivity,
                expiresAt,
                resultSet.getBoolean("is_active")
        );
    }
}
