package com.example.LVTN.dto.Response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendOtpResponse {
    private String sessionInfo;
    private String message;
}