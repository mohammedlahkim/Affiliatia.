package com.example.affiliatia.dto.response;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String slug,
        String description,
        String imageUrl,
        BigDecimal price,
        String merchant,
        String affiliateUrl
) {}