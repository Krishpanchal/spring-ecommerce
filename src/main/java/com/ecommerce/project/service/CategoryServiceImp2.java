package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp2 implements CategoryService {

    List<Category> categories = new ArrayList<Category>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void addCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

        categories.remove(category);
        return "Category with id " + categoryId + " is deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if(optionalCategory.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found");

        Category existingCategory = optionalCategory.get();
        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        return existingCategory;
    }
}
