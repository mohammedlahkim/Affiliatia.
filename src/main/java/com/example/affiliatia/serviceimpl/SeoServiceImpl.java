package com.example.affiliatia.serviceimpl;
import com.example.affiliatia.Entity.Article;
import com.example.affiliatia.service.SeoService;
import org.springframework.stereotype.Service;

@Service
public class SeoServiceImpl implements SeoService {

    @Override
    public void applySeoDefaults(Article article) {

        if (article.getMetaTitle() == null
                || article.getMetaTitle().isBlank()) {

            article.setMetaTitle(
                    article.getTitle()
            );
        }

        if (article.getMetaDescription() == null
                || article.getMetaDescription().isBlank()) {

            article.setMetaDescription(
                    article.getExcerpt()
            );
        }
    }
}