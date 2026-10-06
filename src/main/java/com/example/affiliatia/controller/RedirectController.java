package com.example.affiliatia.controller;

import com.example.affiliatia.Entity.Redirect;
import com.example.affiliatia.dto.event.ClickEventMessage;
import com.example.affiliatia.kafka.ClickEventProducer;
import com.example.affiliatia.service.RedirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final RedirectService redirectService;
    private final ClickEventProducer clickEventProducer;
    @GetMapping("/r/{path}")
    public ResponseEntity<Void> redirect(
            @PathVariable String path,
            @RequestHeader(value = "Referer", required = false) String referrer,
            @RequestHeader(value = "User-Agent", required = false) String userAgent
    ) {

        System.out.println("===== REDIRECT CALLED =====");

        Redirect redirect =
                redirectService.findByFromPath("/r/" + path);

        System.out.println("Redirect found: " + redirect.getFromPath());
        System.out.println("Destination: " + redirect.getToPath());

        ClickEventMessage event = new ClickEventMessage(
                redirect.getArticle() != null
                        ? redirect.getArticle().getId()
                        : null,

                redirect.getProduct() != null
                        ? redirect.getProduct().getId()
                        : null,

                Instant.now(),
                referrer,
                null,
                userAgent
        );

        System.out.println("Event created: " + event);

        clickEventProducer.sendClickEvent(event);

        System.out.println("Event sent to Kafka");

        return ResponseEntity
                .status(redirect.getStatusCode())
                .header(
                        HttpHeaders.LOCATION,
                        redirect.getToPath()
                )
                .build();
    }
}