package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.*;
import com.example.affiliatia.Repository.*;
import com.example.affiliatia.dto.request.ArticleProductItemRequest;
import com.example.affiliatia.dto.request.ArticleRequest;
import com.example.affiliatia.dto.response.ArticleResponse;
import com.example.affiliatia.mapper.ArticleMapper;
import com.example.affiliatia.service.ArticleService;
import com.example.affiliatia.service.SeoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ProductRepository productRepository;
    private final ArticleMapper articleMapper;
    private final SlugService slugService;
    private final SeoService seoService;
    // CREATE
    @Override
    public ArticleResponse create(ArticleRequest request) {

        String slug = request.slug();

        // Aucun slug fourni → génération automatique
        if (slug == null || slug.isBlank()) {

            slug = slugService.generateUniqueSlug(
                    request.title()
            );

        } else {

            // Slug fourni → vérifier son unicité
            if (articleRepository.existsBySlug(slug)) {

                throw new IllegalArgumentException(
                        "Un article avec ce slug existe déjà : " + slug
                );
            }
        }

        Article article = articleMapper.toEntity(request);
        // Important : mettre le slug généré dans l'entité +SEO

        article.setSlug(slug);

        seoService.applySeoDefaults(article);

        article.setUpdatedAt(Instant.now());



        // CATEGORY

        if (request.categoryId() != null) {

            Category category = categoryRepository
                    .findById(request.categoryId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Catégorie introuvable avec l'ID : "
                                            + request.categoryId()
                            )
                    );

            article.setCategory(category);
        }

        // TAGS

        if (request.tagIds() != null
                && !request.tagIds().isEmpty()) {

            List<Tag> tags =
                    tagRepository.findAllById(
                            request.tagIds()
                    );

            article.setTags(tags);
        }

        // PRODUCTS

        if (request.products() != null
                && !request.products().isEmpty()) {

            List<ArticleProduct> articleProducts =
                    buildArticleProducts(
                            article,
                            request.products()
                    );

            article.setArticleProducts(
                    articleProducts
            );
        }

        // SAVE

        Article savedArticle =
                articleRepository.save(article);

        return articleMapper.toResponse(
                savedArticle
        );


    }

    // UPDATE

    @Override
    public ArticleResponse update(
            Long id,
            ArticleRequest request
    ) {

        Article article =
                articleRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Article introuvable avec l'ID : "
                                                + id
                                )
                        );

        // SLUG

        /*
         * Si aucun slug n'est envoyé :
         * → on garde l'ancien slug.
         *
         * Si un nouveau slug est envoyé :
         * → on vérifie qu'il n'est pas déjà utilisé
         *   par un autre article.
         */

        if (request.slug() != null
                && !request.slug().isBlank()) {

            if (articleRepository.existsBySlugAndIdNot(
                    request.slug(),
                    id
            )) {

                throw new IllegalArgumentException(
                        "Un autre article utilise déjà ce slug : "
                                + request.slug()
                );
            }

            article.setSlug(request.slug());
        }
        // UPDATE BASIC FIELDS

        articleMapper.updateEntityFromRequest(
                request,
                article
        );

        article.setUpdatedAt(
                Instant.now()
        );

        // CATEGORY

        if (request.categoryId() != null) {

            Category category =
                    categoryRepository.findById(
                            request.categoryId()
                    ).orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Catégorie introuvable avec l'ID : "
                                            + request.categoryId()
                            )
                    );

            article.setCategory(category);

        } else {

            article.setCategory(null);
        }

        // TAGS

        if (request.tagIds() != null) {

            List<Tag> tags =
                    tagRepository.findAllById(
                            request.tagIds()
                    );

            article.setTags(tags);
        }

        // PRODUCTS

        if (article.getArticleProducts() != null) {

            article.getArticleProducts().clear();

        } else {

            article.setArticleProducts(
                    new ArrayList<>()
            );
        }

        if (request.products() != null
                && !request.products().isEmpty()) {

            List<ArticleProduct> updatedProducts =
                    buildArticleProducts(
                            article,
                            request.products()
                    );

            article.getArticleProducts()
                    .addAll(updatedProducts);
        }

        // SAVE

        Article savedArticle =
                articleRepository.save(article);

        return articleMapper.toResponse(
                savedArticle
        );
    }

    // GET BY ID

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getById(Long id) {

        Article article =
                articleRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Article introuvable avec l'ID : "
                                                + id
                                )
                        );

        return articleMapper.toResponse(article);
    }

    // GET BY SLUG

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getBySlug(String slug) {

        Article article =
                articleRepository.findBySlug(slug)
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        "Article introuvable avec le slug : "
                                                + slug
                                )
                        );

        return articleMapper.toResponse(article);
    }

    // GET ALL

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getAll(
            Pageable pageable
    ) {

        return articleRepository
                .findAll(pageable)
                .map(articleMapper::toResponse);
    }

    // GET BY STATUS

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getByStatus(
            ArticleStatus status,
            Pageable pageable
    ) {

        return articleRepository
                .findByStatus(status, pageable)
                .map(articleMapper::toResponse);
    }

    // GET BY CATEGORY

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getByCategory(
            Long categoryId,
            Pageable pageable
    ) {

        return articleRepository
                .findByCategoryId(
                        categoryId,
                        pageable
                )
                .map(articleMapper::toResponse);
    }

    // SEARCH

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> searchByTitle(
            String title,
            Pageable pageable
    ) {

        return articleRepository
                .findByTitleContainingIgnoreCase(
                        title,
                        pageable
                )
                .map(articleMapper::toResponse);
    }

    // DELETE

    @Override
    public void delete(Long id) {

        if (!articleRepository.existsById(id)) {

            throw new EntityNotFoundException(
                    "Article introuvable avec l'ID : " + id
            );
        }

        articleRepository.deleteById(id);
    }

    // BUILD ARTICLE PRODUCTS
    private List<ArticleProduct> buildArticleProducts(
            Article article,
            List<ArticleProductItemRequest> items
    ) {

        List<ArticleProduct> list =
                new ArrayList<>();

        for (ArticleProductItemRequest item : items) {

            Product product =
                    productRepository.findById(
                            item.productId()
                    ).orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Produit introuvable avec l'ID : "
                                            + item.productId()
                            )
                    );

            ArticleProduct articleProduct =
                    ArticleProduct.builder()
                            .article(article)
                            .product(product)
                            .position(item.position())
                            .badge(item.badge())
                            .build();

            list.add(articleProduct);
        }

        return list;
    }
}