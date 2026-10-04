package com.example.affiliatia.Repository;

import com.example.affiliatia.Entity.ArticleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleProductRepository extends JpaRepository<ArticleProduct, Long> {

    // Récupérer tous les produits d'un article triés par position
    List<ArticleProduct> findByArticleIdOrderByPositionAsc(Long articleId);

    // Supprimer toutes les liaisons pour un article donné
    void deleteByArticleId(Long articleId);
}