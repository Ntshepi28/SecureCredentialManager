package com.securecredentialmanager.repositories;

import com.securecredentialmanager.database.DatabaseConnection;
import com.securecredentialmanager.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CategoryRepository {
    private final Connection connection = DatabaseConnection.getConnection();

    public boolean saveCategory(Category category){
        String sql = """
                INSERT INTO categories
                (id, user_id, name)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, category.getId());
            statement.setLong(2, category.getUserId());
            statement.setString(3, category.getName());

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Category findByName(long userId, String name){
        String sql = """
                SELECT * FROM categories
                WHERE user_id = ? AND name = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, userId);
            statement.setString(2, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                return mapCategory(resultSet);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteCategory(long id){
        String sql = "DELETE FROM categories WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private Category mapCategory(ResultSet resultSet) throws Exception{
        Timestamp createdAtTimestamp = resultSet.getTimestamp("created_at");

        LocalDateTime createdAt = createdAtTimestamp != null
                ? createdAtTimestamp.toLocalDateTime()
                : null;

        return new Category(
                resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getString("name"),
                createdAt
        );
    }
}
