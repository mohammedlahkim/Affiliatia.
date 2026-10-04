package com.example.affiliatia.dto.response;

import com.example.affiliatia.Entity.ArticleStatus;

import java.time.Instant;
import java.util.List;

public record ArticleResponse(
        Long id,
        String title,
        String slug,
        String content,
        String excerpt,
        String metaTitle,
        String metaDescription,
        String canonicalUrl,
        boolean noindex,
        ArticleStatus status,
        Instant publishedAt,
        Instant updatedAt,
        CategoryResponse category,
        List<TagResponse> tags,
        List<ArticleProductResponse> articleProducts
) {}