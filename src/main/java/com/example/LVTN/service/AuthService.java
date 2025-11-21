package com.example.LVTN.service;

import com.example.LVTN.dto.Requests.LoginRequest;
import com.example.LVTN.dto.Requests.RegisterRequest;
import com.example.LVTN.dto.Response.AuthResponses;
import com.example.LVTN.enums.Role;
import com.example.LVTN.entity.User;
import com.example.LVTN.repository.UserRepository;
import com.example.LVTN.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // REGISTER
    public AuthResponses register(RegisterRequest req) {

        if (userRepository.findByPhone(req.getPhone()).isPresent()) {
            throw new RuntimeException("Số điện thoại đã được sử dụng");
        }

        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã được sử dụng");
        }

        User user = User.builder()
                .username(req.getPhone())
                .phone(req.getPhone())
                .password(passwordEncoder.encode(req.getPassword()))
                .fullName(req.getFullName())
                .email(req.getEmail())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(
                user.getPhone(),
                user.getRole().name(),
                user.getFullName(),
                user.getEmail()
        );

        return new AuthResponses(token, user.getFullName(), user.getEmail(), user.getPhone());
    }

    // LOGIN
    public AuthResponses login(LoginRequest req) {

        User user = userRepository.findByPhone(req.getPhone())
                .orElseThrow(() -> new RuntimeException("Số điện thoại không tồn tại"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Sai mật khẩu");
        }

        String token = jwtService.generateToken(
                user.getPhone(),
                user.getRole().name(),
                user.getFullName(),
                user.getEmail()
        );

        return new AuthResponses(token, user.getFullName(), user.getEmail(), user.getPhone());
    }
}


