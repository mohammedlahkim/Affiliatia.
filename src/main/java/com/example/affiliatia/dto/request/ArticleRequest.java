package com.example.affiliatia.dto.request;

import com.example.affiliatia.Entity.ArticleStatus;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.List;
public record ArticleRequest(

        @NotBlank
        @Size(max = 255)
        String title,

        String slug,

        @NotBlank
        String content,

        @Size(max = 500)
        String excerpt,

        @Size(max = 70)
        String metaTitle,

        @Size(max = 160)
        String metaDescription,

        String canonicalUrl,

        boolean noindex,

        @NotNull
        ArticleStatus status,

        @PastOrPresent
        Instant publishedAt,

        Long categoryId,

        List<Long> tagIds,

        List<ArticleProductItemRequest> products
) {}