package com.bankingsystemapplication.service;

import com.bankingsystemapplication.model.Category;
import com.bankingsystemapplication.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category>findAllCategories(){
        return categoryRepository.findAll();
    }
    public Category saveCategory(Category category){
        category.setCompleted(true);
        categoryRepository.save(category);
        return category;
    }
    public Category updateCategory(Long categoryId, Category category){
        Category categoryPersisted=categoryRepository.findById(categoryId).get();
        if (categoryPersisted==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sorry category does not exist");
        }
        categoryPersisted.setId(category.getId());
        categoryPersisted.setCategoryName(category.getCategoryName());
        categoryPersisted.setCompleted(false);
        return categoryRepository.save(categoryPersisted);
    }
    public Category findCategoryById(Long id){
       return categoryRepository.findById(id).get();
    }
    public Category findCategoryByName(String categoryName){
        return categoryRepository.findCategoryByCategoryName(categoryName);
    }
    public void deleteCustomerByID(Long id){
        Category category=categoryRepository.findById(id).get();
        category.setCompleted(false);
    }
    public  boolean isCategoryPresent(String name){
        Category category=categoryRepository.findCategoryByCategoryName(name);
        if (category!=null)
            return true;
        return false;
    }
}
