package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.Article;
import com.example.affiliatia.Repository.ArticleRepository;
import com.example.affiliatia.Repository.ClickEventRepository;
import com.example.affiliatia.Repository.PageViewRepository;
import com.example.affiliatia.dto.analyticsdto.*;
import com.example.affiliatia.service.AnalyticsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    private final ClickEventRepository clickEventRepository;
    private final PageViewRepository pageViewRepository;
    private final ArticleRepository articleRepository;
    @Override
    public TotalClicksResponse getTotalClicks() {
        return new TotalClicksResponse(
                clickEventRepository.count()
        );
    }
    @Override
    public List<TopProductResponse> getTopProducts() {

        return clickEventRepository.findTopProducts()
                .stream()
                .map(row -> new TopProductResponse(
                        (Long) row[0],
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();
    }
    @Override
    public List<TopArticleResponse> getTopArticles() {

        return clickEventRepository.findTopArticles()
                .stream()
                .map(row -> new TopArticleResponse(
                        (Long) row[0],
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();
    }

    @Override
    public List<ClicksPerDayResponse> getClicksPerDay() {
        return clickEventRepository.findClicksPerDay()
                .stream()
                .map(row -> new ClicksPerDayResponse(
                        (LocalDate) row[0],
                        (Long) row[1]
                ))
                .toList();
    }
    @Override
    public List<ClicksPerDayResponse> getClicksLast7Days() {

        Instant startDate =
                Instant.now().minus(java.time.Duration.ofDays(7));

        return clickEventRepository.findClicksLast7Days(startDate)
                .stream()
                .map(row -> new ClicksPerDayResponse(
                        (LocalDate) row[0],
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }
    @Override
    public DashboardResponse getDashboard() {

        return new DashboardResponse(
                clickEventRepository.count(),
                getTopArticles(),
                getTopProducts(),
                getClicksLast7Days(),

                pageViewRepository.count(),
                getTopViewedArticles(),
                getPageViewsLast7Days(),

                getArticleCtr()
        );
    }
    @Override
    public TotalPageViewsResponse getTotalPageViews() {

        return new TotalPageViewsResponse(
                pageViewRepository.count()
        );
    }
    @Override
    public List<TopViewedArticleResponse> getTopViewedArticles() {

        return pageViewRepository.findTopViewedArticles()
                .stream()
                .map(row -> new TopViewedArticleResponse(
                        (Long) row[0],
                        (String) row[1],
                        ((Number) row[2]).longValue()
                ))
                .toList();
    }
    @Override
    public List<PageViewsPerDayResponse> getPageViewsPerDay() {

        return pageViewRepository.findPageViewsPerDay()
                .stream()
                .map(row -> new PageViewsPerDayResponse(
                        (LocalDate) row[0],
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }
    @Override
    public List<PageViewsPerDayResponse> getPageViewsLast7Days() {

        Instant startDate = Instant.now()
                .minus(java.time.Duration.ofDays(7));

        return pageViewRepository.findPageViewsLast7Days(startDate)
                .stream()
                .map(row -> new PageViewsPerDayResponse(
                        (LocalDate) row[0],
                        ((Number) row[1]).longValue()
                ))
                .toList();
    }
    @Override
    public List<ArticleCtrResponse> getArticleCtr() {

        Map<Long, Long> viewsByArticle =
                pageViewRepository.findViewsByArticle()
                        .stream()
                        .collect(Collectors.toMap(
                                row -> (Long) row[0],
                                row -> ((Number) row[1]).longValue()
                        ));


        Map<Long, Long> clicksByArticle =
                clickEventRepository.findClicksByArticle()
                        .stream()
                        .collect(Collectors.toMap(
                                row -> (Long) row[0],
                                row -> ((Number) row[1]).longValue()
                        ));

        return viewsByArticle.entrySet()
                .stream()
                .map(entry -> {

                    Long articleId = entry.getKey();
                    Long views = entry.getValue();

                    Long clicks = clicksByArticle.getOrDefault(
                            articleId,
                            0L
                    );

                    double ctr = views == 0
                            ? 0.0
                            : ((double) clicks / views) * 100;

                    Article article = articleRepository.findById(articleId)
                            .orElseThrow(() ->
                                    new EntityNotFoundException(
                                            "Article introuvable : " + articleId
                                    )
                            );

                    return new ArticleCtrResponse(
                            articleId,
                            article.getTitle(),
                            views,
                            clicks,
                            ctr
                    );
                })
                .sorted(Comparator.comparing(
                        ArticleCtrResponse::ctr
                ).reversed())
                .toList();
    }
}
