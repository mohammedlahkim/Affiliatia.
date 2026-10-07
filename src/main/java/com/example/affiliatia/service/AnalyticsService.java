package com.example.affiliatia.service;

import com.example.affiliatia.dto.analyticsdto.*;

import java.util.List;

public interface AnalyticsService {
    TotalClicksResponse getTotalClicks();
    List<TopProductResponse> getTopProducts();
    List<TopArticleResponse> getTopArticles();
    List<ClicksPerDayResponse> getClicksPerDay();
    List<ClicksPerDayResponse> getClicksLast7Days();
    DashboardResponse getDashboard();
    }

