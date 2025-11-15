package com.example.LVTN.repository;

import com.example.LVTN.dto.ScamReportDTO;
import com.example.LVTN.entity.ScamReportEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ScamReportMapper {
    ScamReportDTO toDTO(ScamReportEntity entity);

    ScamReportEntity toEntity(ScamReportDTO dto);
}
