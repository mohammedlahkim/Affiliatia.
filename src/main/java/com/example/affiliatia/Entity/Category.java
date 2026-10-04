package com.example.affiliatia.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Le slug est obligatoire")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Le slug doit contenir uniquement des lettres minuscules, chiffres et tirets")
    @Column(nullable = false, unique = true)
    private String slug;

    @Size(max = 255, message = "La description ne doit pas dépasser 255 caractères")
    private String description;

    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();
}
