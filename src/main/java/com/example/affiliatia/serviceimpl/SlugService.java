package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SlugService {

    private final ArticleRepository articleRepository;

    public String generateUniqueSlug(String title) {

        String baseSlug = generateSlug(title);

        if (baseSlug.isBlank()) {
            throw new IllegalArgumentException(
                    "Impossible de générer un slug à partir du titre"
            );
        }

        String slug = baseSlug;
        int counter = 2;

        while (articleRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + counter;
            counter++;
        }

        return slug;
    }

    private String generateSlug(String text) {

        String normalized = Normalizer.normalize(
                text,
                Normalizer.Form.NFD
        );

        return normalized
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("^-|-$", "");
    }
}