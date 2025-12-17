//package com.example.LVTN.controller;
//
//import com.example.LVTN.dto.Response.ApiResponse;
//import com.example.LVTN.service.AuthService;
//import com.example.LVTN.dto.Requests.LoginRequest;
//import com.example.LVTN.dto.Response.AuthResponses;
//import com.example.LVTN.dto.Requests.RegisterRequest;
//import com.example.LVTN.service.AuthService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//
//    private final AuthService authService;
//
//
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthResponses> register(@RequestBody RegisterRequest request) {
//        return ResponseEntity.ok(authService.register(request));
//    }
//
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponses> login(@RequestBody LoginRequest request) {
//        return ResponseEntity.ok(authService.login(request));
//    }
//
//    @PostMapping("/auth/logout")
//    public ResponseEntity<ApiResponse<Void>> logout() {
//        // Với JWT: không cần làm gì ở server, chỉ cần client xóa token
//        return ResponseEntity.ok(ApiResponse.<Void>builder()
//                .message("Đăng xuất thành công")
//                .build());
//    }
//
//}
