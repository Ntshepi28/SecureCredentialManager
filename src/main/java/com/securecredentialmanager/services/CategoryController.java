package com.securecredentialmanager.services;

import com.securecredentialmanager.models.Category;
import com.securecredentialmanager.repositories.CategoryRepository;

public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(){
        this.categoryRepository = new CategoryRepository();
    }

    public boolean createCategory(long id, int userId, String name){
        if (userId <= 0){
            return false;
        }

        if (name == null || name.isBlank()){
            return false;
        }

        Category category = new Category();

        category.setId(id);
        category.setUserId(userId);
        category.setName(name);

        return categoryRepository.saveCategory(category);
    }

    public Category findByName(int userId, String name){

        if (userId <= 0 || name == null || name.isBlank()){
            return null;
        }

        return categoryRepository.findByName(userId, name);
    }

    public int getCategoryCount(int userId){
        if (userId <= 0){
            return 0;
        }

        return categoryRepository.countByUserId(userId);
    }
}
