package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundExcetion;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("No category created till now.");
        return categories;
    }

    @Override
    public void addCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(Objects.nonNull(savedCategory))
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundExcetion("Category", "categoryId", categoryId));
        categoryRepository.delete(savedCategory);
        return "Category with id " + categoryId + " is deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundExcetion("Category", "categoryId", categoryId));


        updatedCategory.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(updatedCategory);
        return savedCategory;
    }
}
