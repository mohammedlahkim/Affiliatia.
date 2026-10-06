package com.example.affiliatia.kafka;

import com.example.affiliatia.Entity.Article;
import com.example.affiliatia.Entity.ClickEvent;
import com.example.affiliatia.Entity.Product;
import com.example.affiliatia.Repository.ArticleRepository;
import com.example.affiliatia.Repository.ClickEventRepository;
import com.example.affiliatia.Repository.ProductRepository;
import com.example.affiliatia.dto.event.ClickEventMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClickEventConsumer {

    private final ClickEventRepository clickEventRepository;
    private final ArticleRepository articleRepository;
    private final ProductRepository productRepository;

    @KafkaListener(
            topics = "click-events",
            groupId = "affiliatia-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ClickEventMessage event) {

        System.out.println("===== CLICK EVENT RECEIVED =====");
        System.out.println("EVENT = " + event);

        Article article = null;
        Product product = null;

        if (event.articleId() != null) {
            article = articleRepository.findById(event.articleId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Article introuvable : "
                                            + event.articleId()
                            )
                    );
        }

        if (event.productId() != null) {
            product = productRepository.findById(event.productId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Produit introuvable : "
                                            + event.productId()
                            )
                    );
        }

        ClickEvent clickEvent = ClickEvent.builder()
                .clickedAt(event.clickedAt())
                .referrer(event.referrer())
                .ipHash(event.ipHash())
                .userAgent(event.userAgent())
                .article(article)
                .product(product)
                .build();

        clickEventRepository.save(clickEvent);

        System.out.println("CLICK EVENT SAVED : " + clickEvent.getId());
    }
}