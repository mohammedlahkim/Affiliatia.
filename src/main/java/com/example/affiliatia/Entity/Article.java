package com.example.affiliatia.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 255, message = "Le titre ne doit pas dépasser 255 caractères")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Le slug est obligatoire")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Le slug doit être en minuscules et séparé par des tirets")
    @Column(nullable = false, unique = true)
    private String slug;

    @NotBlank(message = "Le contenu ne peut pas être vide")
    @Column(columnDefinition = "TEXT")
    private String content;

    @Size(max = 500, message = "L'extrait ne doit pas dépasser 500 caractères")
    @Column(columnDefinition = "TEXT")
    private String excerpt;

    @Size(max = 70, message = "Le metaTitle doit faire au maximum 70 caractères pour le SEO")
    private String metaTitle;

    @Size(max = 160, message = "La metaDescription doit faire au maximum 160 caractères pour le SEO")
    @Column(columnDefinition = "TEXT")
    private String metaDescription;

    @Pattern(regexp = "^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$", message = "L'URL canonical doit être une URL valide")
    private String canonicalUrl;

    private boolean noindex;

    @NotNull(message = "Le statut de l'article est obligatoire")
    @Enumerated(EnumType.STRING)
    private ArticleStatus status;

    @PastOrPresent(message = "La date de publication ne peut pas être dans le futur")
    private Instant publishedAt;

    @PastOrPresent(message = "La date de mise à jour ne peut pas être dans le futur")
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "article_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "article_id")
    private List<ArticleImage> images;

    @OneToMany(mappedBy = "article")
    private List<ArticleProduct> articleProducts;

    @OneToMany(mappedBy = "article")
    private List<PageView> pageViews;

    @OneToMany(mappedBy = "article")
    private List<ClickEvent> clickEvents;
}