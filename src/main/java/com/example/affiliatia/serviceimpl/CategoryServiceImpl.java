package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.Category;
import com.example.affiliatia.Repository.CategoryRepository;
import com.example.affiliatia.dto.request.CategoryRequest;
import com.example.affiliatia.dto.response.CategoryResponse;
import com.example.affiliatia.mapper.CategoryMapper;
import com.example.affiliatia.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    @Override
    public CategoryResponse create(CategoryRequest request) {

        if(categoryRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Slug already exists");
        }
        if(categoryRepository.existsByName(request.getName())) {
            throw new RuntimeException("Name already exists");
        }
        Category category= categoryMapper.toEntity(request);

        Category saved = categoryRepository.save(category);

        return categoryMapper.toResponse(saved);
    }
    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll(){
        List<Category> categories=categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toResponse)
                .toList();
        //       List<CategoryResponse> responses = new ArrayList<>();
        //    for (Category category : categories) {
        //        responses.add(categoryMapper.toResponse(category));
        //    }
    }
    @Override
    public CategoryResponse update(Long id,CategoryRequest request){
        Category category=categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        categoryRepository.save(category);
        return categoryMapper.toResponse(category);
    }
    @Override
    public void delete(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }
    @Transactional(readOnly = true)
    public CategoryResponse findBySlug(String slug){
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toResponse(category);
    }
}