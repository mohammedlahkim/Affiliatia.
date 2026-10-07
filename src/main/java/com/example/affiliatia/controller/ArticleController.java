package com.example.affiliatia.controller;

import com.example.affiliatia.Entity.Article;
import com.example.affiliatia.Entity.ArticleStatus;
import com.example.affiliatia.Repository.ArticleRepository;
import com.example.affiliatia.dto.request.ArticleRequest;
import com.example.affiliatia.dto.response.ArticleResponse;
import com.example.affiliatia.service.ArticleService;
import com.example.affiliatia.service.PageViewService;
import com.example.affiliatia.service.SchemaMarkupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Articles", description = "Endpoints de gestion des articles et produits affiliés en cascade")
public class ArticleController {

    private final ArticleService articleService;
    private final PageViewService pageViewService;

    @Operation(summary = "Créer un article", description = "Crée un nouvel article avec ses catégories, tags et produits affiliés liés.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou slug déjà existant")
    })
    @PostMapping
    public ResponseEntity<ArticleResponse> create(@Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.create(request));
    }

    @Operation(summary = "Mettre à jour un article", description = "Met à jour un article existant et réorganise la liste de ses produits affiliés.")
    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.ok(articleService.update(id, request));
    }

    @Operation(summary = "Obtenir un article par ID")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    @Operation(summary = "Obtenir un article par Slug (SEO)")
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ArticleResponse> getBySlug(
            @PathVariable String slug,
            @RequestHeader(value = "Referer", required = false) String referrer
    ) {

        ArticleResponse article = articleService.getBySlug(slug);

        pageViewService.recordPageView(
                "/api/v1/articles/slug/" + slug,
                referrer,
                article.id()
        );

        return ResponseEntity.ok(article);
    }

    @Operation(summary = "Lister les articles avec pagination")
    @GetMapping
    public ResponseEntity<Page<ArticleResponse>> getAll(
            @PageableDefault(size = 10, sort = "updatedAt") Pageable pageable) {
        return ResponseEntity.ok(articleService.getAll(pageable));
    }

    @Operation(summary = "Supprimer un article")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/schema")
    public ResponseEntity<String> getSchema(@PathVariable Long id) {

        return ResponseEntity.ok(
                articleService.generateSchema(id)
        );
    }
}