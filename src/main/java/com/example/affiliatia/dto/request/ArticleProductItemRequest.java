package com.example.affiliatia.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ArticleProductItemRequest(
        @NotNull(message = "L'ID du produit est obligatoire")
        Long productId,

        @Min(value = 1, message = "La position doit être supérieure ou égale à 1")
        int position,

        @Size(max = 50, message = "Le badge ne doit pas dépasser 50 caractères")
        String badge
) {}