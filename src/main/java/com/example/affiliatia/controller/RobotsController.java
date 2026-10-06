package com.example.affiliatia.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotsController {

    @GetMapping(
            value = "/robots.txt",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> getRobots() {

        String robots = """
                User-agent: *
                Allow: /

                Sitemap: http://localhost:8080/sitemap.xml
                """;

        return ResponseEntity.ok(robots);
    }
}