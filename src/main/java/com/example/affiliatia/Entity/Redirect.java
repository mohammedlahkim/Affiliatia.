package com.example.affiliatia.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
@Entity
@Table(name = "redirects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Redirect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le chemin d'origine (fromPath) est obligatoire")
    @Column(nullable = false)
    private String fromPath;

    @NotBlank(message = "Le chemin de destination (toPath) est obligatoire")
    @Column(nullable = false)
    private String toPath;

    @Min(value = 300, message = "Le code HTTP doit être un code de redirection valide (ex: 301, 302)")
    @Max(value = 399, message = "Le code HTTP doit être un code de redirection valide")
    private int statusCode;
}