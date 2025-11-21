package com.example.LVTN.dto.Requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String phone;
    private String password;
}
