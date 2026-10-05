package com.example.affiliatia.controller;

import com.example.affiliatia.dto.request.CategoryRequest;
import com.example.affiliatia.dto.response.CategoryResponse;
import com.example.affiliatia.serviceimpl.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final CategoryServiceImpl categoryService;
    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request) {
        return categoryService.create(request);
    }

   /* @GetMapping("/{slug}")
        public CategoryResponse findBySlug(@PathVariable String slug) {
        return categoryService.findBySlug(slug);
    }
    TODO Could be useful later ON */
    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Long id){
        return categoryService.findById(id);
    }
    @GetMapping
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id , @Valid @RequestBody CategoryRequest request){
        return categoryService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
    categoryService.delete(id);
    }



}
