package com.example.LVTN.repository;

import com.example.LVTN.entity.ScamReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScamReportRepository extends JpaRepository<ScamReportEntity, Long> {

    // Lấy danh sách report theo SĐT
    List<ScamReportEntity> findByPhoneOrderByCreatedAtDesc(String phone);
    List<ScamReportEntity> findByReporterPhoneOrderByCreatedAtDesc(String reporterPhone);

    // Đếm số report của 1 SĐT trong 1 khoảng thời gian
    @Query("""
    SELECT COUNT(s)
    FROM ScamReportEntity s
    WHERE s.reporterPhone = :reporterPhone
      AND s.createdAt >= :start
      AND s.createdAt < :end
""")
    int countByReporterPhoneAndCreatedAtBetween(
            @Param("reporterPhone") String reporterPhone,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
