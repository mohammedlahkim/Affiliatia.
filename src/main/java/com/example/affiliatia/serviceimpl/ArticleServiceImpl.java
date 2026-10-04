package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.*;
import com.example.affiliatia.Repository.*;
import com.example.affiliatia.dto.request.ArticleProductItemRequest;
import com.example.affiliatia.dto.request.ArticleRequest;
import com.example.affiliatia.dto.response.ArticleResponse;
import com.example.affiliatia.mapper.ArticleMapper;
import com.example.affiliatia.service.ArticleService;
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

    @Override
    public ArticleResponse create(ArticleRequest request) {
        if (articleRepository.existsBySlug(request.slug())) {
            throw new IllegalArgumentException("Un article avec ce slug existe déjà : " + request.slug());
        }

        Article article = articleMapper.toEntity(request);
        article.setUpdatedAt(Instant.now());

        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Catégorie introuvable avec l'ID : " + request.categoryId()));
            article.setCategory(category);
        }

        if (request.tagIds() != null && !request.tagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(request.tagIds());
            article.setTags(tags);
        }

        if (request.products() != null && !request.products().isEmpty()) {
            List<ArticleProduct> articleProducts = buildArticleProducts(article, request.products());
            article.setArticleProducts(articleProducts);
        }

        Article savedArticle = articleRepository.save(article);
        return articleMapper.toResponse(savedArticle);
    }

    @Override
    public ArticleResponse update(Long id, ArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article introuvable avec l'ID : " + id));

        if (articleRepository.existsBySlugAndIdNot(request.slug(), id)) {
            throw new IllegalArgumentException("Un autre article utilise déjà ce slug : " + request.slug());
        }

        articleMapper.updateEntityFromRequest(request, article);
        article.setUpdatedAt(Instant.now());

        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Catégorie introuvable avec l'ID : " + request.categoryId()));
            article.setCategory(category);
        } else {
            article.setCategory(null);
        }

        if (request.tagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(request.tagIds());
            article.setTags(tags);
        }

        if (article.getArticleProducts() != null) {
            article.getArticleProducts().clear();
        } else {
            article.setArticleProducts(new ArrayList<>());
        }

        if (request.products() != null && !request.products().isEmpty()) {
            List<ArticleProduct> updatedProducts = buildArticleProducts(article, request.products());
            article.getArticleProducts().addAll(updatedProducts);
        }

        return articleMapper.toResponse(articleRepository.save(article));
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Article introuvable avec l'ID : " + id));
        return articleMapper.toResponse(article);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse getBySlug(String slug) {
        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException("Article introuvable avec le slug : " + slug));
        return articleMapper.toResponse(article);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getAll(Pageable pageable) {
        return articleRepository.findAll(pageable)
                .map(articleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getByStatus(ArticleStatus status, Pageable pageable) {
        return articleRepository.findByStatus(status, pageable)
                .map(articleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> getByCategory(Long categoryId, Pageable pageable) {
        return articleRepository.findByCategoryId(categoryId, pageable)
                .map(articleMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticleResponse> searchByTitle(String title, Pageable pageable) {
        return articleRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(articleMapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new EntityNotFoundException("Article introuvable avec l'ID : " + id);
        }
        articleRepository.deleteById(id);
    }

    private List<ArticleProduct> buildArticleProducts(Article article, List<ArticleProductItemRequest> items) {
        List<ArticleProduct> list = new ArrayList<>();
        for (ArticleProductItemRequest item : items) {
            Product product = productRepository.findById(item.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Produit introuvable avec l'ID : " + item.productId()));

            ArticleProduct articleProduct = ArticleProduct.builder()
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