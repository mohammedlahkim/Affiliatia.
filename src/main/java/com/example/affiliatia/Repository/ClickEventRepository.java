package com.example.affiliatia.Repository;

import com.example.affiliatia.Entity.ClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    @Query("""
SELECT COUNT(c)
FROM ClickEvent c
""")
    long countAllClicks();
    @Query("""
    SELECT c.product.id, c.product.name, COUNT(c)
    FROM ClickEvent c
    WHERE c.product IS NOT NULL
    GROUP BY c.product.id, c.product.name
    ORDER BY COUNT(c) DESC
""")
    List<Object[]> findTopProducts();
    @Query("""
    SELECT c.article.id, c.article.title, COUNT(c)
    FROM ClickEvent c
    WHERE c.article IS NOT NULL
    GROUP BY c.article.id, c.article.title
    ORDER BY COUNT(c) DESC
""")
    List<Object[]> findTopArticles();
    @Query("""
    SELECT CAST(c.clickedAt AS LocalDate), COUNT(c)
    FROM ClickEvent c
    GROUP BY CAST(c.clickedAt AS LocalDate)
    ORDER BY CAST(c.clickedAt AS LocalDate)
""")
    List<Object[]> findClicksPerDay();

    @Query("""
    SELECT CAST(c.clickedAt AS LocalDate), COUNT(c)
    FROM ClickEvent c
    WHERE c.clickedAt >= :startDate
    GROUP BY CAST(c.clickedAt AS LocalDate)
    ORDER BY CAST(c.clickedAt AS LocalDate)
""")
    List<Object[]> findClicksLast7Days(
            @Param("startDate") Instant startDate
    );
}