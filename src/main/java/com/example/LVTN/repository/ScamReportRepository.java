package com.example.LVTN.repository;

import com.example.LVTN.entity.ScamReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScamReportRepository extends JpaRepository<ScamReportEntity, Long> {
    List<ScamReportEntity> findByPhone(String phone);

}
