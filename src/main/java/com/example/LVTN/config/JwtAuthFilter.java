//package com.example.LVTN.config;
//
//import com.example.LVTN.entity.User;
//import com.example.LVTN.repository.UserRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class JwtAuthFilter extends OncePerRequestFilter {
//
//    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
//
//    private final UserRepository userRepository;
//
//    public JwtAuthFilter(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.isAuthenticated()) {
//
//            String phone = authentication.getName(); // ✅ subject = phone
//
//            User user = userRepository.findByPhone(phone).orElse(null);
//
//            logger.info("User {} gọi endpoint {}", phone, request.getRequestURI());
//
//            if (user == null || Boolean.FALSE.equals(user.getIsActive())) {
//                sendJsonResponse(response,
//                        HttpServletResponse.SC_FORBIDDEN,
//                        "Tài khoản đã bị vô hiệu hóa.");
//                return;
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private void sendJsonResponse(HttpServletResponse response, int status, String message) throws IOException {
//        response.setStatus(status);
//        response.setContentType("application/json");
//        Map<String, Object> body = new HashMap<>();
//        body.put("code", status);
//        body.put("message", message);
//        response.getWriter().write(
//                new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(body)
//        );
//    }
//}
