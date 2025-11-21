package com.example.LVTN.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScamReportResponse {

    private Long id;
    private String reporterName;
    private String phone;
    private String email;
    private String scamType;
    private String description;
    private String evidenceLink;
    private String status;
    private LocalDateTime createdAt;
}

