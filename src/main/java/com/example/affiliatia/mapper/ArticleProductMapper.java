package com.example.affiliatia.mapper;

import com.example.affiliatia.Entity.ArticleProduct;
import com.example.affiliatia.dto.response.ArticleProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        uses = {ProductMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ArticleProductMapper {

    ArticleProductResponse toResponse(ArticleProduct articleProduct);
}