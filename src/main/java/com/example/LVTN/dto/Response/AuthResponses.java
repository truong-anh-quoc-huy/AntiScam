package com.example.LVTN.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AuthResponses {
    private String token;
    private String fullName;
    private String email;
    private String phone;
}
