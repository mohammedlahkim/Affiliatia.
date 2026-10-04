package com.example.affiliatia.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.time.Instant;

@Entity
@Table(name = "page_views")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le chemin consulté est obligatoire")
    private String path;

    @NotNull(message = "L'horodatage est obligatoire")
    @PastOrPresent(message = "La date de vue ne peut pas être dans le futur")
    private Instant viewedAt;

    private String referrer;

    private boolean bot;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
