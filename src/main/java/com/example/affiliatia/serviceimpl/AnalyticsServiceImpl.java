package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Repository.ClickEventRepository;
import com.example.affiliatia.dto.analyticsdto.*;
import com.example.affiliatia.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    private final ClickEventRepository clickEventRepository;
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
                getClicksLast7Days()
        );
    }
}
