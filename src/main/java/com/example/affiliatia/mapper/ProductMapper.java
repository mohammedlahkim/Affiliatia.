package com.example.affiliatia.mapper;

import com.example.affiliatia.Entity.Product;
import com.example.affiliatia.dto.request.ProductRequest;
import com.example.affiliatia.dto.response.ProductResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    Product toEntity(ProductRequest request);

    void updateEntityFromRequest(ProductRequest request, @MappingTarget Product product);
}