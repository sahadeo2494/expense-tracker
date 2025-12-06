package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.dto.CategoryDTO;
import com.example.expense_tracker.entity.Category;
import com.example.expense_tracker.entity.User;
import com.example.expense_tracker.repository.CategoryRepository;
import com.example.expense_tracker.service.CategoryService;
import com.example.expense_tracker.util.mapper.CategoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserServiceImpl userService;

    @Override
    public List<CategoryDTO> getALLCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map( category -> categoryMapper.toDTO(category))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));

        return categoryMapper.toDTO(category);
    }

    @Override
    public Category getCategoryIdByUserIdAndCategoryName(Long userId, String categoryName) {
        return categoryRepository.findByUserIdAndNameIgnoreCase(userId, categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found for user_id " + userId + " category_name " + categoryName));
    }

    @Override
    public CategoryDTO crateCategory(CategoryDTO categoryDTO) {
        //check user exist
        User user = userService.getUserByEmailAndUsername(categoryDTO.getEmail(), categoryDTO.getUsername());

        Category category = categoryMapper.toEntity(categoryDTO);
        // Attache user to category
        category.setUser(user);
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        // Get User
        User user = userService.getUserByEmailAndUsername(categoryDTO.getEmail(), categoryDTO.getUsername());

        // Get Category on id
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));

        // Authorization/Onwership check
        if(!category.getUser().getId().equals(user.getId())){
            throw new RuntimeException("You cannot update someone else's Category");
        }

        category.setColor(categoryDTO.getColor());
        category.setType(categoryDTO.getType());
        category.setName(categoryDTO.getName());

        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
       categoryRepository.deleteById(id);
    }
}
