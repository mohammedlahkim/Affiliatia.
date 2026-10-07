package com.example.affiliatia.dto.analyticsdto;

import java.util.List;

public record DashboardResponse(
        long totalClicks,
        List<TopArticleResponse> topArticles,
        List<TopProductResponse> topProducts,
        List<ClicksPerDayResponse> clicksLast7Days
) {}