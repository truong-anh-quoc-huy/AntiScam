package com.example.LVTN.repository;

import com.example.LVTN.dto.Requests.ScamReportRequest;
import com.example.LVTN.dto.Response.ScamReportResponse;
import com.example.LVTN.entity.ScamReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScamReportMapper {

    @Mapping(source = "reporterPhone", target = "reporterPhone")
    ScamReportResponse toResponseDTO(ScamReportEntity entity);

    ScamReportEntity toEntity(ScamReportRequest dto);
}

