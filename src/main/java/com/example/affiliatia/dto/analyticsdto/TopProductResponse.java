package com.example.affiliatia.dto.analyticsdto;

public record TopProductResponse(
        Long productId,
        String name,
        Long clicks
) {}