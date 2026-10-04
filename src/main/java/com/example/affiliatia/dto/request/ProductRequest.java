package com.example.affiliatia.dto.request;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Le nom du produit est obligatoire")
        @Size(max = 150, message = "Le nom ne doit pas dépasser 150 caractères")
        String name,

        @NotBlank(message = "Le slug du produit est obligatoire")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Le slug doit être en minuscules et séparé par des tirets")
        String slug,

        String description,

        String imageUrl,

        @NotNull(message = "Le prix est obligatoire")
        @PositiveOrZero(message = "Le prix doit être positif ou égal à zéro")
        BigDecimal price,

        String merchant,

        String affiliateUrl
) {}