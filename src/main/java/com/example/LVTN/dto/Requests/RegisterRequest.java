package com.example.LVTN.dto.Requests;


import lombok.Data;

@Data
public class RegisterRequest {
    private String phone;
    private String password;
    private String fullName;
    private String email;
}


