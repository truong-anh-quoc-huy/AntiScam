package com.example.LVTN.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "scam_numbers")
public class ScamNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phone;

    private Long reportCount = 0L;

    private LocalDateTime lastReportAt;

    private String status = "SUSPECT"; // hoặc: SUSPECT, VERIFIED
}
