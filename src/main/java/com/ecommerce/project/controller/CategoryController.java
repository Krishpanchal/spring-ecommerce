package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category){
        categoryService.addCategory(category);
        return new ResponseEntity<>("Category added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try{
            String status =  categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(status);
        }catch(ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestBody Category category){
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, category);
            return new ResponseEntity<>("Category with id " + categoryId + " is updated successfully",HttpStatus.OK);
        }catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }
    }
}
