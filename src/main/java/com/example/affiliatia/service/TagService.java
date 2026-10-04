package com.example.affiliatia.service;

import com.example.affiliatia.dto.request.TagRequest;
import com.example.affiliatia.dto.response.TagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    TagResponse create(TagRequest request);

    TagResponse update(Long id, TagRequest request);

    TagResponse getById(Long id);

    TagResponse getBySlug(String slug);

    List<TagResponse> getAllList();

    Page<TagResponse> getAll(Pageable pageable);

    Page<TagResponse> searchByName(String name, Pageable pageable);

    void delete(Long id);
}