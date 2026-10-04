package com.example.affiliatia.dto.response;

public record ArticleProductResponse(
        Long id,
        Long articleId,
        String articleTitle,
        ProductResponse product,
        int position,
        String badge
) {}