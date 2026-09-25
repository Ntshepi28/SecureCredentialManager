package com.securecredentialmanager.services;

import com.securecredentialmanager.models.Category;
import com.securecredentialmanager.repositories.CategoryRepository;

import java.util.Collections;
import java.util.List;

public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService() {
        this.categoryRepository = new CategoryRepository();
    }

    /**
     * Creates a new category for a specific user after validating inputs.
     */
    public boolean createCategory(int userId, String name) {
        if (userId <= 0) {
            System.err.println("Category creation failed: Invalid User ID.");
            return false;
        }

        if (name == null || name.isBlank()) {
            System.err.println("Category creation failed: Category name cannot be empty.");
            return false;
        }

        return categoryRepository.saveCategory(userId, name.trim());
    }

    /**
     * Retrieves all categories created by a specific user.
     */
    public List<Category> getCategoriesForUser(int userId) {
        if (userId <= 0) {
            return Collections.emptyList();
        }

        return categoryRepository.getCategoriesByUserId(userId);
    }

    /**
     * Finds a category by its name and associated user ID.
     */
    public Category findByName(long userId, String name) {
        if (userId <= 0 || name == null || name.isBlank()) {
            return null;
        }

        return categoryRepository.findByName(userId, name.trim());
    }

    /**
     * Deletes a category by its primary key ID.
     */
    public boolean deleteCategory(long categoryId) {
        if (categoryId <= 0) {
            return false;
        }

        return categoryRepository.deleteCategory(categoryId);
    }

    /**
     * Returns the total number of categories created by a user.
     */
    public int getCategoryCount(int userId) {
        if (userId <= 0) {
            return 0;
        }

        return categoryRepository.countByUserId(userId);
    }
}