package com.example.LVTN.repository;

import com.example.LVTN.dto.Requests.ScamReportRequest;
import com.example.LVTN.dto.Response.ScamReportResponse;
import com.example.LVTN.entity.ScamReportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScamReportMapper {
    ScamReportEntity toEntity(ScamReportRequest dto); // bỏ qua id, createdAt
    ScamReportResponse toResponseDTO(ScamReportEntity entity); // map đầy đủ
}

