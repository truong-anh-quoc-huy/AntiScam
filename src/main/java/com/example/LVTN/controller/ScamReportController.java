package com.example.LVTN.controller;

import com.example.LVTN.dto.Requests.ScamReportRequest;
import com.example.LVTN.dto.Response.ApiResponse;
import com.example.LVTN.dto.Response.CheckScamResponse;
import com.example.LVTN.dto.Response.ScamReportResponse;
//import com.example.LVTN.service.JwtService;
import com.example.LVTN.service.ScamReportService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/reports")
public class ScamReportController {
//    private final JwtService jwtService;
    private final ScamReportService service;

    public ScamReportController(ScamReportService service) {
        this.service = service;
//        this.jwtService = jwtService;
    }

    // -------------------- GET ALL --------------------
    @Operation(
            summary = "Lấy danh sách báo cáo",
            description = "Trả về toàn bộ danh sách báo cáo lừa đảo có trong hệ thống"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScamReportResponse>>> getAllReports() {
        List<ScamReportResponse> reports = service.getAllReports();
        return ResponseEntity.ok(ApiResponse.<List<ScamReportResponse>>builder()
                .message("Lấy danh sách báo cáo thành công")
                .data(reports)
                .build());
    }

    // -------------------- GET BY ID --------------------
    @Operation(
            summary = "Lấy báo cáo theo ID",
            description = "Dùng ID để lấy thông tin chi tiết của một báo cáo"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScamReportResponse>> getReport(@PathVariable Long id) {
        ScamReportResponse dto = service.getReportById(id);
        return ResponseEntity.ok(ApiResponse.<ScamReportResponse>builder()
                .message("Lấy báo cáo theo ID thành công")
                .data(dto)
                .build());
    }

    // -------------------- CHECK PHONE --------------------
    @Operation(
            summary = "Kiểm tra số điện thoại có phải lừa đảo không",
            description = "Trả về số lượt báo cáo, mức độ nguy hiểm và thông tin liên quan"
    )
    @GetMapping("/check")
    public ResponseEntity<ApiResponse<CheckScamResponse>> checkScam(@RequestParam String phone) {
        CheckScamResponse result = service.checkScam(phone);

        return ResponseEntity.ok(
                ApiResponse.<CheckScamResponse>builder()
                        .message("Kiểm tra số điện thoại thành công")
                        .data(result)
                        .build()
        );
    }

    // -------------------- CREATE REPORT --------------------
    @Operation(
            summary = "Tạo báo cáo mới",
            description = "Người dùng gửi báo cáo số điện thoại nghi ngờ lừa đảo"
    )
//    @PostMapping("/create")
//    public ResponseEntity<ApiResponse<ScamReportResponse>> createReport(
//            @AuthenticationPrincipal Jwt jwt,
//            @RequestBody ScamReportRequest request) {
//
//        Long userId = Long.valueOf(jwt.getClaimAsString("sub"));
//
//        ScamReportResponse response = service.createReport(userId, request);
//
//        return ResponseEntity.ok(
//                ApiResponse.<ScamReportResponse>builder()
//                        .message("Gửi báo cáo thành công")
//                        .data(response)
//                        .build()
//        );
//    }
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ScamReportResponse>> createReport(
            @RequestBody ScamReportRequest request) {

        ScamReportResponse response = service.createReport(null, request);

        return ResponseEntity.ok(
                ApiResponse.<ScamReportResponse>builder()
                        .message("Gửi báo cáo thành công")
                        .data(response)
                        .build()
        );
    }



    // -------------------- UPDATE STATUS --------------------
//    @Operation(
//            summary = "Cập nhật trạng thái xử lý báo cáo",
//            description = "Admin cập nhật trạng thái báo cáo như: pending / approved / rejected"
//    )
//    @PutMapping("/{id}/status")
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<ApiResponse<ScamReportResponse>> updateStatus(
//            @PathVariable Long id,
//            @RequestParam String status) {
//
//        ScamReportResponse updated = service.updateStatus(id, status);
//        return ResponseEntity.ok(ApiResponse.<ScamReportResponse>builder()
//                .message("Cập nhật trạng thái thành công")
//                .data(updated)
//                .build());
//    }


    // -------------------- DELETE REPORT --------------------
//    @Operation(
//            summary = "Xóa báo cáo",
//            description = "Xóa báo cáo khỏi hệ thống theo ID"
//    )
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')") // chỉ Admin mới được gọi
//    public ResponseEntity<ApiResponse<Void>> deleteReport(@PathVariable Long id) {
//        service.deleteReport(id);
//        return ResponseEntity.ok(ApiResponse.<Void>builder()
//                .message("Xóa báo cáo thành công")
//                .data(null)
//                .build());
//    }
//    @Operation(
//            summary = "Xem lich su bao cao",
//            description = "Xem lich su bao cao"
//    )
//    @GetMapping("/my-reports")
//    public ResponseEntity<ApiResponse<List<ScamReportResponse>>> getMyReports(
//            @AuthenticationPrincipal Jwt jwt
//    ) {
//
//        Long userId = Long.valueOf(jwt.getClaimAsString("sub"));
//
//        List<ScamReportResponse> reports = service.getReportsByUser(userId);
//
//        return ResponseEntity.ok(
//                ApiResponse.<List<ScamReportResponse>>builder()
//                        .message("Lấy lịch sử báo cáo thành công")
//                        .data(reports)
//                        .build()
//        );
//    }

}