package com.example.affiliatia.service;

import com.example.affiliatia.Entity.ArticleStatus;
import com.example.affiliatia.dto.request.ArticleRequest;
import com.example.affiliatia.dto.response.ArticleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

    ArticleResponse create(ArticleRequest request);

    ArticleResponse update(Long id, ArticleRequest request);

    ArticleResponse getById(Long id);

    ArticleResponse getBySlug(String slug);

    Page<ArticleResponse> getAll(Pageable pageable);

    Page<ArticleResponse> getByStatus(ArticleStatus status, Pageable pageable);

    Page<ArticleResponse> getByCategory(Long categoryId, Pageable pageable);

    Page<ArticleResponse> searchByTitle(String title, Pageable pageable);

    void delete(Long id);
}