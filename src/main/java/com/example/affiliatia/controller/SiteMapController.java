package com.example.affiliatia.controller;

import com.example.affiliatia.service.SiteMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/xmlsitemap")
public class SiteMapController {

    private final SiteMapService sitemapService;

    @GetMapping(
            value = "/sitemap.xml",
            produces = MediaType.APPLICATION_XML_VALUE
    )
    public ResponseEntity<String> getSitemap() {

        return ResponseEntity.ok(
                sitemapService.generateSitemap()
        );
    }
}