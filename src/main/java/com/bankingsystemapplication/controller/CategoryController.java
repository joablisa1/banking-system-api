package com.bankingsystemapplication.controller;

import com.bankingsystemapplication.model.Category;
import com.bankingsystemapplication.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Category>>findAllCategoryEntity(){
        return new ResponseEntity<>(categoryService.findAllCategories(),HttpStatus.OK);
    }
    @PostMapping("/category/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category>saveCategoryResponseEntity(@RequestBody Category category){
        if(categoryService.isCategoryPresent(category.getCategoryName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Sorry category exist in the system");
        }
        category.setCompleted(true);

        return new ResponseEntity<>(categoryService.saveCategory(category),HttpStatus.CREATED);
    }
    @GetMapping("/category/find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category>findCategoryByIdEntity(@PathVariable("id") Long id){
        Category category=categoryService.findCategoryById(id);
        if(category!=null){
            return new ResponseEntity<>(category, HttpStatus.ACCEPTED);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/category/update/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category>updateCategoryResponseEntity(@PathVariable("categoryId")Long categoryId,@RequestBody Category category){
        category.setId(categoryId);
        return  new ResponseEntity<>(categoryService.updateCategory(categoryId,category),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/category/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id")Long id ){
        categoryService.deleteCustomerByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
