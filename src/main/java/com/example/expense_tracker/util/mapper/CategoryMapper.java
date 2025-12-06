package com.example.expense_tracker.util.mapper;

import com.example.expense_tracker.dto.CategoryDTO;
import com.example.expense_tracker.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setColor(category.getColor());
        categoryDTO.setType(category.getType());
        categoryDTO.setEmail(category.getUser().getEmail());
        categoryDTO.setUsername(category.getUser().getUsername());
        return categoryDTO;
    }

    public Category toEntity(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setColor(categoryDTO.getColor());
        category.setType(categoryDTO.getType());
        category.setName(categoryDTO.getName());
        return category;
    }

}
