package com.example.affiliatia.dto.analyticsdto;

public record TopViewedArticleResponse(
        Long articleId,
        String title,
        Long views
) {}