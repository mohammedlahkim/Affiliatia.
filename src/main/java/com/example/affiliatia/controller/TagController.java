package com.example.affiliatia.controller;

import com.example.affiliatia.dto.request.TagRequest;
import com.example.affiliatia.dto.response.TagResponse;
import com.example.affiliatia.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagResponse> create(@Valid @RequestBody TagRequest request) {
        TagResponse createdTag = tagService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody TagRequest request) {
        return ResponseEntity.ok(tagService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tagService.getById(id));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<TagResponse> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(tagService.getBySlug(slug));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TagResponse>> getAllList() {
        return ResponseEntity.ok(tagService.getAllList());
    }

    @GetMapping
    public ResponseEntity<Page<TagResponse>> getAll(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(tagService.getAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TagResponse>> searchByName(
            @RequestParam String name,
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(tagService.searchByName(name, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}