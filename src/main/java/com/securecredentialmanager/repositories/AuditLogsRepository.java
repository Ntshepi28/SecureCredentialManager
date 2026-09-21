package com.securecredentialmanager.repositories;

import com.securecredentialmanager.database.DatabaseConnection;
import com.securecredentialmanager.models.AuditLogs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AuditLogsRepository {
    private final Connection connection = DatabaseConnection.getConnection();

    public boolean saveAuditLogs(AuditLogs auditLogs){
        String sql = """
                INSERT INTO audit_logs
                (user_id, action, description, ip_address, device_name)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, auditLogs.getUserId());
            statement.setString(2, auditLogs.getAction());
            statement.setString(3, auditLogs.getDescription());
            statement.setString(4, auditLogs.getIpAddress());
            statement.setString(5, auditLogs.getDeviceName());

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public AuditLogs findById(long id){
        String sql = """
                SELECT * FROM audit_logs
                WHERE id = ?
                """;


        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapAuditLog(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public AuditLogs findLatestByUserId(int userId){
        String sql = """
                SELECT * FROM audit_logs
                WHERE user_id = ?
                ORDER BY created_at DESC
                LIMIT 1
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapAuditLog(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteAuditLog(long id){
        String sql = """
                DELETE FROM audit_logs
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private AuditLogs mapAuditLog(ResultSet rs) throws Exception {

        Timestamp createdAtTimestamp = rs.getTimestamp("created_at");

        LocalDateTime createdAt = createdAtTimestamp != null
                ? createdAtTimestamp.toLocalDateTime()
                : null;

        return new AuditLogs(
                rs.getLong("id"),
                rs.getInt("user_id"),
                rs.getString("action"),
                rs.getString("description"),
                rs.getString("ip_address"),
                rs.getString("device_name"),
                createdAt
        );
    }
}
