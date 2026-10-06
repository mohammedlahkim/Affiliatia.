package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.Article;
import com.example.affiliatia.Entity.ArticleStatus;
import com.example.affiliatia.Repository.ArticleRepository;
import com.example.affiliatia.service.SiteMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteMapServiceImpl implements SiteMapService {

    private final ArticleRepository articleRepository;

    @Override
    public String generateSitemap() {

        List<Article> articles =
                articleRepository
                        .findByStatusAndNoindexFalse(
                                ArticleStatus.PUBLISHED
                        );

        StringBuilder xml = new StringBuilder();

        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");

        for (Article article : articles) {

            xml.append("<url>");

            xml.append("<loc>");
            xml.append("http://localhost:8080/articles/")
                    .append(article.getSlug());
            xml.append("</loc>");

            if (article.getUpdatedAt() != null) {
                xml.append("<lastmod>");
                xml.append(article.getUpdatedAt());
                xml.append("</lastmod>");
            }

            xml.append("</url>");
        }

        xml.append("</urlset>");

        return xml.toString();
    }
}