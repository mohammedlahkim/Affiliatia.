package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.Article;
import com.example.affiliatia.service.SchemaMarkupService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchemaMarkupServiceImpl implements SchemaMarkupService {

    private final ObjectMapper objectMapper;
    @Override
    public String generateArticleSchema(Article article) {

        Map<String, Object> schema = new LinkedHashMap<>();
        Map<String, Object> mainEntity = new LinkedHashMap<>();

        mainEntity.put("@type", "WebPage");
        mainEntity.put(
                "@id",
                "http://localhost:8080/articles/" + article.getSlug()
        );

        schema.put("mainEntityOfPage", mainEntity);
        schema.put(
                "@context",
                "https://schema.org"
        );

        schema.put(
                "@type",
                "Article"
        );

        schema.put(
                "headline",
                article.getTitle()
        );

        schema.put(
                "description",
                article.getMetaDescription()
        );

        schema.put(
                "datePublished",
                article.getPublishedAt()
        );

        schema.put(
                "dateModified",
                article.getUpdatedAt()
        );

        if (    article.getAuthor() != null) {

            Map<String, Object> author = new LinkedHashMap<>();

            author.put(
                    "@type",
                    "Person"
            );

            author.put(
                    "name",
                    article.getAuthor().getEmail()
            );

            schema.put(
                    "author",
                    author
            );
        }
        if (article.getCategory() != null) {
            schema.put("articleSection", article.getCategory().getName());
        }

        try {

            return objectMapper.writeValueAsString(schema);

        } catch (JsonProcessingException e) {

            throw new IllegalStateException(
                    "Erreur lors de la génération du JSON-LD",
                    e
            );
        }
    }
}