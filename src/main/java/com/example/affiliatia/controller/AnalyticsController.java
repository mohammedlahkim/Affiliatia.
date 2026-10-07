package com.example.affiliatia.controller;

import com.example.affiliatia.dto.analyticsdto.*;
import com.example.affiliatia.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/clicks")
    public TotalClicksResponse getClicks() {
        return analyticsService.getTotalClicks();
    }
    @GetMapping("/top-products")
    public List<TopProductResponse> getTopProducts() {
        return analyticsService.getTopProducts();
    }
    @GetMapping("/top-articles")
    public List<TopArticleResponse> getTopArticles() {
        return analyticsService.getTopArticles();
    }
    @GetMapping("/clicks-per-day")
    public List<ClicksPerDayResponse> getClicksPerDay() {
        return analyticsService.getClicksPerDay();
    }
    @GetMapping("/clicks-last-7-days")
    public List<ClicksPerDayResponse> getClicksLast7Days() {
        return analyticsService.getClicksLast7Days();
    }
    @GetMapping("/dashboard")
    public DashboardResponse getDashboard() {
        return analyticsService.getDashboard();
    }
    @GetMapping("/page-views")
    public TotalPageViewsResponse getPageViews() {
        return analyticsService.getTotalPageViews();
    }
    @GetMapping("/top-viewed-articles")
    public List<TopViewedArticleResponse> getTopViewedArticles() {
        return analyticsService.getTopViewedArticles();
    }
    @GetMapping("/page-views-per-day")
    public List<PageViewsPerDayResponse> getPageViewsPerDay() {
        return analyticsService.getPageViewsPerDay();
    }
    @GetMapping("/page-views-last-7-days")
    public List<PageViewsPerDayResponse> getPageViewsLast7Days() {
        return analyticsService.getPageViewsLast7Days();
    }
    @GetMapping("/article-ctr")
    public List<ArticleCtrResponse> getArticleCtr() {
        return analyticsService.getArticleCtr();
    }


}