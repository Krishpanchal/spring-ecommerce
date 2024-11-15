package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name="sortBy",defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(name="sortOrder",defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder,
            Sort sort){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO category){
        CategoryDTO savedCategory = categoryService.addCategory(category);
        return new ResponseEntity<CategoryDTO>(savedCategory  ,HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@Valid @PathVariable Long categoryId){
            CategoryDTO categoryDTO =  categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(categoryDTO,HttpStatus.OK);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO){
            CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
            return new ResponseEntity<CategoryDTO>(updatedCategory,HttpStatus.OK);

    }
}
