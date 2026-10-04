package com.example.affiliatia.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// FAIT DE FACON NORMALE SANS RECORD
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String name;

    @NotBlank(message = "Le slug est obligatoire")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Le slug doit contenir uniquement des lettres minuscules, chiffres et tirets")
    private String slug;

    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
    private String description;

}
