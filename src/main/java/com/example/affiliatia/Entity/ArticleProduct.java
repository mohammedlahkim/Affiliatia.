package com.example.affiliatia.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;
@Entity
@Table(name = "article_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "La position doit être supérieure ou égale à 1")
    private int position;

    @Size(max = 50, message = "Le badge ne doit pas dépasser 50 caractères (ex: 'Meilleur Choix')")
    private String badge;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}