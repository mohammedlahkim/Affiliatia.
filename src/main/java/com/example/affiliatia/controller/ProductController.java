package com.example.affiliatia.controller;

import com.example.affiliatia.dto.request.ProductRequest;
import com.example.affiliatia.dto.response.ProductResponse;
import com.example.affiliatia.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse createdProduct = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProductResponse> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(productService.getBySlug(slug));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAll(
            @PageableDefault(size = 12, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(productService.getAll(pageable));
    }

    @GetMapping("/merchant/{merchant}")
    public ResponseEntity<Page<ProductResponse>> getByMerchant(
            @PathVariable String merchant,
            @PageableDefault(size = 12, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(productService.getByMerchant(merchant, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponse>> searchByName(
            @RequestParam String name,
            @PageableDefault(size = 12, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(productService.searchByName(name, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}