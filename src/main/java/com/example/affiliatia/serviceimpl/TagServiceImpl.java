package com.example.affiliatia.serviceimpl;

import com.example.affiliatia.Entity.Tag;
import com.example.affiliatia.Repository.TagRepository;
import com.example.affiliatia.dto.request.TagRequest;
import com.example.affiliatia.dto.response.TagResponse;
import com.example.affiliatia.mapper.TagMapper;
import com.example.affiliatia.service.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public TagResponse create(TagRequest request) {
        if (tagRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Un tag avec ce nom existe déjà : " + request.name());
        }
        if (tagRepository.existsBySlug(request.slug())) {
            throw new IllegalArgumentException("Un tag avec ce slug existe déjà : " + request.slug());
        }

        Tag tag = tagMapper.toEntity(request);
        Tag savedTag = tagRepository.save(tag);
        return tagMapper.toResponse(savedTag);
    }

    @Override
    public TagResponse update(Long id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag introuvable avec l'ID : " + id));

        if (tagRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new IllegalArgumentException("Un autre tag utilise déjà ce nom : " + request.name());
        }
        if (tagRepository.existsBySlugAndIdNot(request.slug(), id)) {
            throw new IllegalArgumentException("Un autre tag utilise déjà ce slug : " + request.slug());
        }

        tagMapper.updateEntityFromRequest(request, tag);
        return tagMapper.toResponse(tagRepository.save(tag));
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponse getById(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag introuvable avec l'ID : " + id));
        return tagMapper.toResponse(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public TagResponse getBySlug(String slug) {
        Tag tag = tagRepository.findBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException("Tag introuvable avec le slug : " + slug));
        return tagMapper.toResponse(tag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagResponse> getAllList() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagResponse> getAll(Pageable pageable) {
        return tagRepository.findAll(pageable)
                .map(tagMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TagResponse> searchByName(String name, Pageable pageable) {
        return tagRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(tagMapper::toResponse);
    }

    @Override
    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : Tag introuvable avec l'ID : " + id);
        }
        tagRepository.deleteById(id);
    }
}