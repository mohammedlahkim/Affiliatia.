package com.example.affiliatia.mapper;

import com.example.affiliatia.Entity.Category;
import com.example.affiliatia.dto.request.CategoryRequest;
import com.example.affiliatia.dto.response.CategoryResponse;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .build();
    }
    public Category toEntity(CategoryRequest categoryRequest){

        return Category.builder()
                .name(categoryRequest.getName())
                .slug(categoryRequest.getSlug())
                .description((categoryRequest.getDescription()))
                .build();
    }
}