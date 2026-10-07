package com.example.affiliatia.service;

public interface PageViewService {

    void recordPageView(
            String path,
            String referrer,
            Long articleId
    );
}