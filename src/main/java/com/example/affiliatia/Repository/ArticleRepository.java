package com.example.affiliatia.Repository;

import com.example.affiliatia.Entity.Article;
import com.example.affiliatia.Entity.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findBySlug(String slug);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);

    Page<Article> findByStatus(ArticleStatus status, Pageable pageable);

    Page<Article> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Article> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    List<Article> findByStatus(ArticleStatus status);

    List<Article> findByStatusAndNoindexFalse(
            ArticleStatus status);

}