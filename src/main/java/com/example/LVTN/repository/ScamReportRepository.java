package com.example.LVTN.repository;

import com.example.LVTN.entity.ScamReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScamReportRepository extends JpaRepository<ScamReportEntity, Long> {
    List<ScamReportEntity> findByPhone(String phone);
    @Query("SELECT COUNT(s) FROM ScamReportEntity s WHERE s.userId = :userId AND s.createdAt >= :start AND s.createdAt < :end")
    int countByUserIdAndCreatedAtBetween(@Param("userId") Long userId,
                                         @Param("start") LocalDateTime start,
                                         @Param("end") LocalDateTime end);


}
