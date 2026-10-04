package com.example.affiliatia.Repository;

import com.example.affiliatia.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug(String slug);
    public boolean existsBySlug(String slug);
    public boolean existsByName(String name);
}
