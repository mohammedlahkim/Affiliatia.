package com.example.affiliatia.mapper;

import com.example.affiliatia.Entity.Tag;
import com.example.affiliatia.dto.request.TagRequest;
import com.example.affiliatia.dto.response.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TagMapper {

    TagResponse toResponse(Tag tag);

    Tag toEntity(TagRequest request);

    void updateEntityFromRequest(TagRequest request, @MappingTarget Tag tag);
}