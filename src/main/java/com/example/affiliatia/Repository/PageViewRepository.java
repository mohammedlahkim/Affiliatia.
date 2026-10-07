package com.example.affiliatia.Repository;

import com.example.affiliatia.Entity.PageView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface PageViewRepository extends JpaRepository<PageView, Long> {
    @Query("""
    SELECT p.article.id, p.article.title, COUNT(p)
    FROM PageView p
    WHERE p.article IS NOT NULL
    GROUP BY p.article.id, p.article.title
    ORDER BY COUNT(p) DESC
""")
    List<Object[]> findTopViewedArticles();
    @Query("""
    SELECT CAST(p.viewedAt AS LocalDate), COUNT(p)
    FROM PageView p
    GROUP BY CAST(p.viewedAt AS LocalDate)
    ORDER BY CAST(p.viewedAt AS LocalDate)
""")
    List<Object[]> findPageViewsPerDay();
    @Query("""
    SELECT CAST(p.viewedAt AS LocalDate), COUNT(p)
    FROM PageView p
    WHERE p.viewedAt >= :startDate
    GROUP BY CAST(p.viewedAt AS LocalDate)
    ORDER BY CAST(p.viewedAt AS LocalDate)
""")
    List<Object[]> findPageViewsLast7Days(
            @Param("startDate") Instant startDate
    );
    @Query("""
    SELECT p.article.id, COUNT(p)
    FROM PageView p
    WHERE p.article IS NOT NULL
    GROUP BY p.article.id
""")
    List<Object[]> findViewsByArticle();
}
