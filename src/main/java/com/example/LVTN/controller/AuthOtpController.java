//package com.example.LVTN.controller;
//
//import com.example.LVTN.dto.Requests.SendOtpRequest;
//import com.example.LVTN.dto.Requests.VerifyOtpRequest;
//import com.example.LVTN.dto.Response.AuthResponses;
//import com.example.LVTN.dto.Response.SendOtpResponse;
//import com.example.LVTN.dto.Response.VerifyOtpResponse;
//import com.example.LVTN.service.AuthService;
//import com.example.LVTN.service.FirebaseAuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthOtpController {
//
//    private final FirebaseAuthService firebaseAuthService;
//    private final AuthService authService;
//
//    @PostMapping("/send-otp")
//    public ResponseEntity<SendOtpResponse> sendOtp(
//            @RequestBody SendOtpRequest request
//    ) {
//        String sessionInfo = firebaseAuthService.sendOtp(request.getPhone());
//
//        return ResponseEntity.ok(
//                new SendOtpResponse(sessionInfo, "OTP đã được gửi")
//        );
//    }
//
//    @PostMapping("/verify-otp")
//    public ResponseEntity<VerifyOtpResponse> verifyOtp(
//            @RequestBody VerifyOtpRequest request
//    ) {
//        String phone = firebaseAuthService.verifyOtp(
//                request.getSessionInfo(),
//                request.getCode()
//        );
//
//        AuthResponses auth = authService.loginOrRegisterByOtp(phone);
//
//        return ResponseEntity.ok(
//                new VerifyOtpResponse(
//                        phone,
//                        auth.getToken(),
//                        "Đăng nhập thành công"
//                )
//        );
//    }
//}
//
