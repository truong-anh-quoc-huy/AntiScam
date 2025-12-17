package com.example.LVTN.dto.Requests;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String sessionInfo;
    private String code; // OTP người dùng nhập
}
