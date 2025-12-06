package com.example.expense_tracker.service;


import com.example.expense_tracker.dto.CategoryDTO;
import com.example.expense_tracker.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getALLCategory();
    CategoryDTO getCategoryById(Long id);
    Category getCategoryIdByUserIdAndCategoryName(Long userId, String categoryName);
    CategoryDTO crateCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
 }
