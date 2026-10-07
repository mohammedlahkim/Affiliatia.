package com.example.affiliatia.dto.analyticsdto;

public record TopArticleResponse(
        Long articleId,
        String title,
        Long clicks
){}
