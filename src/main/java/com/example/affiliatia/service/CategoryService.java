package com.example.affiliatia.service;

import com.example.affiliatia.dto.request.CategoryRequest;
import com.example.affiliatia.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll();

    CategoryResponse update(Long id, CategoryRequest request);

    CategoryResponse findBySlug(String slug);

    void delete(Long id);
}
