package com.securecredentialmanager.repositories;

import com.securecredentialmanager.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryRepositoryTest {
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp(){
        categoryRepository = new CategoryRepository();

        categoryRepository.deleteCategory(1001);
    }

    @Test
    void shouldSaveAndFindCategory(){
        Category category = new Category();
        category.setId(1001);
        category.setUserId(10);
        category.setName("Anathi");

        boolean saved = categoryRepository.saveCategory(category);

        assertTrue(saved);

        Category foundCategory = categoryRepository.findByName(10, "Anathi");

        assertNotNull(foundCategory);
        assertEquals(1001, foundCategory.getId());
        assertEquals(10, foundCategory.getUserId());
        assertEquals("Anathi", foundCategory.getName());
        assertNotNull(foundCategory.getCreatedAt());
    }

    @Test
    void shouldDeleteCategory(){
        Category category = new Category();
        category.setId(1001);
        category.setUserId(10);
        category.setName("Anathi");

        categoryRepository.saveCategory(category);

        boolean deleted = categoryRepository.deleteCategory(1001);

        assertTrue(deleted);

        Category foundCategory = categoryRepository.findByName(10, "Anathi");

        assertNull(foundCategory);
    }
}
