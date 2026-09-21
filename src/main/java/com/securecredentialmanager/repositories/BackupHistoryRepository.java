package com.securecredentialmanager.repositories;

import com.securecredentialmanager.database.DatabaseConnection;
import com.securecredentialmanager.models.BackupHistory;

import java.sql.*;
import java.time.LocalDateTime;

public class BackupHistoryRepository {
    private final Connection connection = DatabaseConnection.getConnection();

    public boolean saveBackup(BackupHistory backupHistory){

        String sql = """
                INSERT INTO backup_history
                (user_id, backup_name, backup_path, backup_size, status)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement =connection.prepareStatement(sql)){
            statement.setInt(1, backupHistory.getUserId());
            statement.setString(2, backupHistory.getBackupName());
            statement.setString(3, backupHistory.getBackupPath());
            statement.setInt(4, backupHistory.getBackupSize());
            statement.setString(5, backupHistory.getStatus());

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public BackupHistory findById(long id){
        String sql = """
                SELECT * FROM backup_history
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapBackupHistory(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public BackupHistory findLatestByUserId(int userId){
        String sql = """
                SELECT * FROM backup_history
                WHERE user_id = ?
                ORDER BY created_at DESC
                LIMIT 1
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapBackupHistory(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteBackup(long id){
        String sql = """
                DELETE FROM backup_history
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private BackupHistory mapBackupHistory(ResultSet resultSet) throws Exception {

        Timestamp createdAtTimestamp = resultSet.getTimestamp("created_at");

        LocalDateTime createdAt = createdAtTimestamp != null
                ? createdAtTimestamp.toLocalDateTime()
                : null;

        return new BackupHistory(
                resultSet.getLong("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("backup_name"),
                resultSet.getString("backup_path"),
                resultSet.getInt("backup_size"),
                resultSet.getString("status"),
                createdAt
        );
    }

}
