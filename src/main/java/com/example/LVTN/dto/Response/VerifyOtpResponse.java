package com.example.LVTN.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyOtpResponse {
    private String phone;
    private String accessToken;
    private String message;
}