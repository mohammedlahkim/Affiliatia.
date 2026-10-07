package com.example.affiliatia.dto.analyticsdto;

public record ArticleCtrResponse(
        Long articleId,
        String title,
        Long views,
        Long clicks,
        double ctr
) {}