package com.example.LVTN.dto;

import com.example.LVTN.validation.AtLeastOneNotEmpty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@AtLeastOneNotEmpty(fields = {"email", "phone"}, message = "Phải nhập ít nhất email hoặc số điện thoại")
public class ScamReportDTO {

    private Long id;

    @NotBlank(message = "Tên người báo cáo không được để trống")
    private String reporterName;

    private String phone;

    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Loại lừa đảo không được để trống")
    private String scamType;

    private String description;
    private String evidenceLink;
    private String status;
    private LocalDateTime createdAt;
}
