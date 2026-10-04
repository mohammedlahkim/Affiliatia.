    package com.example.affiliatia.Entity;

    import jakarta.persistence.*;
    import lombok.*;
    import jakarta.validation.constraints.*;

    import java.math.BigDecimal;
    import java.util.List;
    @Entity
    @Table(name = "products")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Le nom du produit est obligatoire")
        @Column(nullable = false)
        private String name;

        @NotBlank(message = "Le slug du produit est obligatoire")
        @Column(nullable = false, unique = true)
        private String slug;

        @Column(columnDefinition = "TEXT")
        private String description;

        private String imageUrl;

        @NotNull(message = "Le prix est obligatoire")
        @PositiveOrZero(message = "Le prix doit être positif ou égal à zéro")
        private BigDecimal price;

        private String merchant;

        private String affiliateUrl;

        @OneToMany(mappedBy = "product")
        private List<ArticleProduct> articleProducts;
    }