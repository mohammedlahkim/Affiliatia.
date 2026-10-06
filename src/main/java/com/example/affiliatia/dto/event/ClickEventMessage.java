package com.example.affiliatia.dto.event;

import java.time.Instant;

public record ClickEventMessage(
        Long articleId,
        Long productId,
        Instant clickedAt,
        String referrer,
        String ipHash,
        String userAgent
) {
}