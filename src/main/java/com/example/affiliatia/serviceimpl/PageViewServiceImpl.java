package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.Article;
import com.example.affiliatia.Entity.PageView;
import com.example.affiliatia.Repository.ArticleRepository;
import com.example.affiliatia.Repository.PageViewRepository;
import com.example.affiliatia.service.PageViewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PageViewServiceImpl implements PageViewService {

    private final PageViewRepository pageViewRepository;
    private final ArticleRepository articleRepository;

    @Override
    @Transactional
    public void recordPageView(
            String path,
            String referrer,
            Long articleId
    ) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Article introuvable : " + articleId
                        )
                );

        PageView pageView = PageView.builder()
                .path(path)
                .viewedAt(Instant.now())
                .referrer(referrer)
                .bot(false)
                .article(article)
                .build();

        pageViewRepository.save(pageView);
    }
}