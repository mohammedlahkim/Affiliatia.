package com.example.affiliatia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TagRequest(
        @NotBlank(message = "Le nom du tag est obligatoire")
        @Size(max = 50, message = "Le nom du tag ne doit pas dépasser 50 caractères")
        String name,

        @NotBlank(message = "Le slug est obligatoire")
        @Pattern(regexp = "^[a-z0-9-]+$", message = "Le slug doit être en minuscules et séparé par des tirets")
        String slug
) {}