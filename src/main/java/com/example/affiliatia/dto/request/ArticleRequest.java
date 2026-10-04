package com.example.affiliatia.dto.request;

import com.example.affiliatia.Entity.ArticleStatus;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.List;

public record ArticleRequest(
        @NotBlank(message = "Le titre est obligatoire")
        @Size(max = 255, message = "Le titre ne doit pas dépasser 255 caractères")
        String title,

        @NotBlank(message = "Le slug est obligatoire")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Le slug doit être en minuscules et séparé par des tirets")
        String slug,

        @NotBlank(message = "Le contenu ne peut pas être vide")
        String content,

        @Size(max = 500, message = "L'extrait ne doit pas dépasser 500 caractères")
        String excerpt,

        @Size(max = 70, message = "Le metaTitle doit faire au maximum 70 caractères pour le SEO")
        String metaTitle,

        @Size(max = 160, message = "La metaDescription doit faire au maximum 160 caractères pour le SEO")
        String metaDescription,

        @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$", message = "L'URL canonical doit être une URL valide")
        String canonicalUrl,

        boolean noindex,

        @NotNull(message = "Le statut de l'article est obligatoire")
        ArticleStatus status,

        @PastOrPresent(message = "La date de publication ne peut pas être dans le futur")
        Instant publishedAt,

        Long categoryId,

        List<Long> tagIds,

        List<ArticleProductItemRequest> products
) {}