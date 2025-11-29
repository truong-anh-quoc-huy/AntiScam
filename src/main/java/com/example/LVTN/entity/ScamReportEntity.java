package com.example.LVTN.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "scam_reports")
public class ScamReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String reporterName;
    private String email;
    private String phone;
    private String scamType;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String evidenceLink;
    private String status = "PENDING";
    private LocalDateTime createdAt = LocalDateTime.now();
}

