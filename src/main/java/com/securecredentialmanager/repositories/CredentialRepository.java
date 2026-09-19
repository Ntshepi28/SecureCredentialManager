package com.securecredentialmanager.repositories;

import com.securecredentialmanager.database.DatabaseConnection;
import com.securecredentialmanager.models.Credential;

import java.sql.*;
import java.time.LocalDateTime;

public class CredentialRepository {

    private final Connection connection = DatabaseConnection.getConnection();

    public boolean saveCredential(Credential credential){
        String sql = """
                INSERT INTO credentials
                (user_id, category_id, service_name, website,
                 login_username, encrypted_password, notes)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, credential.getCategoryId());

            if (credential.getCategoryId() != null){
                statement.setInt(2, credential.getCategoryId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }

            statement.setString(3, credential.getServiceName());
            statement.setString(4, credential.getWebsite());
            statement.setString(5, credential.getLoginUsername());
            statement.setString(6, credential.getEncryptedPassword());
            statement.setString(7, credential.getNotes());

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Credential findById(int id){
        String sql = "SELECT * FROM credentials WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapCredential(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return  null;
    }

    public Credential findByServiceName(int userId, String serviceName){

        String sql = """
                SELECT * FROM credentials
                WHERE user_id = ? AND service_name = ?
                """;


        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, userId);
            statement.setString(2, serviceName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapCredential(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
