package com.example.affiliatia.mapper;

import com.example.affiliatia.Entity.Article;
import com.example.affiliatia.dto.request.ArticleRequest;
import com.example.affiliatia.dto.response.ArticleResponse;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {
                CategoryMapper.class,
                TagMapper.class,
                ArticleProductMapper.class
        },
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ArticleMapper {

    ArticleResponse toResponse(Article article);

    Article toEntity(ArticleRequest request);

    @Mapping(target = "slug", ignore = true)
    void updateEntityFromRequest(
            ArticleRequest request,
            @MappingTarget Article article
    );
}