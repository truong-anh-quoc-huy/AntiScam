package com.example.LVTN.service;


import org.springframework.beans.factory.annotation.Value; // ✅ ĐÚNG
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class FirebaseAuthService {

    @Value("${firebase.api-key}")
    private String firebaseApiKey;

    private final RestTemplate rest = new RestTemplate();

    public String sendOtp(String phone) {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:sendVerificationCode?key=" + firebaseApiKey;

        Map<String, Object> body = new HashMap<>();
        body.put("phoneNumber", phone);

        Map response = rest.postForObject(url, body, Map.class);

        return (String) response.get("sessionInfo");
    }

    public String verifyOtp(String sessionInfo, String code) {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPhoneNumber?key=" + firebaseApiKey;

        Map<String, Object> body = new HashMap<>();
        body.put("sessionInfo", sessionInfo);
        body.put("code", code);

        Map response = rest.postForObject(url, body, Map.class);

        return (String) response.get("phoneNumber"); // lấy số điện thoại
    }
}
