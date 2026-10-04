package com.example.affiliatia.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
import java.time.LocalDate;


@Entity
@Table(name = "search_query_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchQueryStat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La requête de recherche est obligatoire")
    private String query;

    private String pageUrl;

    @PositiveOrZero(message = "Les impressions doivent être supérieures ou égales à zéro")
    private int impressions;

    @PositiveOrZero(message = "Les clics doivent être supérieurs ou égaux à zéro")
    private int clicks;

    @PositiveOrZero(message = "La position moyenne doit être supérieure ou égale à zéro")
    private double position;

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}