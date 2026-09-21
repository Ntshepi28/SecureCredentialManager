package com.securecredentialmanager.repositories;

import com.securecredentialmanager.database.DatabaseConnection;
import com.securecredentialmanager.models.PasswordHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PasswordHistoryRepository {
    private final Connection connection = DatabaseConnection.getConnection();

    public boolean savePasswordHistory(PasswordHistory passwordHistory){

        String sql = """
                INSERT INTO password_history
                (credential_id, encrypted_password)
                VALUES (?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, passwordHistory.getCredentialId());
            statement.setString(2, passwordHistory.getEncryptedPassword());

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public PasswordHistory findById(long id){
        String sql = """
                SELECT * FROM password_history
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapPasswordHistory(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public PasswordHistory findLatestByCredentialId(int credentialId){
        String sql = """
                SELECT * FROM password_history
                WHERE credential_id = ?
                ORDER BY changed_at DESC
                LIMIT 1
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, credentialId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapPasswordHistory(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean deletePasswordHistory(long id){
        String sql = """
                DELETE FROM password_history
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

    private PasswordHistory mapPasswordHistory(ResultSet resultSet) throws Exception{

        Timestamp changedAtTimestamp = resultSet.getTimestamp("changed_at");

        LocalDateTime changeAt = changedAtTimestamp != null
                ? changedAtTimestamp.toLocalDateTime()
                : null;

        return new PasswordHistory(
                resultSet.getLong("id"),
                resultSet.getInt("credential_id"),
                resultSet.getString("encrypted_password"),
                changeAt
        );
    }
}
