package com.bankingsystemapplication.repository;

import com.bankingsystemapplication.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findCategoryByCategoryName(String categoryName);
}
