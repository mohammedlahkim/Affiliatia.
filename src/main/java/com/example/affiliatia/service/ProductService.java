package com.example.affiliatia.service;


import com.example.affiliatia.dto.request.ProductRequest;
import com.example.affiliatia.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductResponse create(ProductRequest request);

    ProductResponse update(Long id, ProductRequest request);

    ProductResponse getById(Long id);

    ProductResponse getBySlug(String slug);

    Page<ProductResponse> getAll(Pageable pageable);

    Page<ProductResponse> getByMerchant(String merchant, Pageable pageable);

    Page<ProductResponse> searchByName(String name, Pageable pageable);

    void delete(Long id);
}