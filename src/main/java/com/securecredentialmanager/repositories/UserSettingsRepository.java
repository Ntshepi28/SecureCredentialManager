package com.securecredentialmanager.repositories;

import com.securecredentialmanager.database.DatabaseConnection;
import com.securecredentialmanager.models.UserSettings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class UserSettingsRepository {
    private final Connection connection = DatabaseConnection.getConnection();

    public boolean saveSettings(UserSettings userSettings){
        String sql = """
                INSERT INTO user_settings
                (user_id, theme, auto_lock_minutes,
                 password_generator_length, require_master_password)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userSettings.getUserId());
            statement.setString(2, userSettings.getTheme());
            statement.setInt(3, userSettings.getAutoLockMinutes());
            statement.setInt(4, userSettings.getPasswordGeneratorLength());
            statement.setBoolean(5, userSettings.isRequireMasterPassword());

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public UserSettings findByUserId(int userId){
        String sql = """
                SELECT * FROM user_settings
                WHERE user_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapSettings(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateSettings(UserSettings userSettings){
        String sql = """
                UPDATE user_settings
                SET theme = ?,
                    auto_lock_minutes = ?,
                    password_generator_length = ?,
                    require_master_password = ?
                WHERE user_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, userSettings.getTheme());
            statement.setInt(2, userSettings.getAutoLockMinutes());
            statement.setInt(3, userSettings.getPasswordGeneratorLength());
            statement.setBoolean(4, userSettings.isRequireMasterPassword());
            statement.setInt(5, userSettings.getUserId());

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }

    public boolean deleteSettings(int userId){
        String sql = """
                DELETE FROM user_settings
                WHERE user_id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserSettings mapSettings(ResultSet resultSet) throws Exception{
        Timestamp createdAtTimestamp = resultSet.getTimestamp("created_at");
        Timestamp updatedAtTimestamp = resultSet.getTimestamp("updated_at");

        LocalDateTime createdAt = createdAtTimestamp != null
                ? createdAtTimestamp.toLocalDateTime()
                : null;

        LocalDateTime updatedAt = updatedAtTimestamp != null
                ? updatedAtTimestamp.toLocalDateTime()
                : null;


        return new UserSettings(
                resultSet.getLong("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("theme"),
                resultSet.getInt("auto_lock_minutes"),
                resultSet.getInt("password_generator_length"),
                resultSet.getBoolean("require_master_password"),
                createdAt,
                updatedAt
        );
    }
}
