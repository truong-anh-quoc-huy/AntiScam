package com.example.LVTN.controller;

import com.example.LVTN.dto.ApiResponse;
import com.example.LVTN.dto.CheckScamResponse;
import com.example.LVTN.dto.ScamReportDTO;
import com.example.LVTN.service.ScamReportService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ScamReportController {

    private final ScamReportService service;

    public ScamReportController(ScamReportService service) {
        this.service = service;
    }

    // -------------------- GET ALL --------------------
    @Operation(
            summary = "Lấy danh sách báo cáo",
            description = "Trả về toàn bộ danh sách báo cáo lừa đảo có trong hệ thống"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ScamReportDTO>>> getAllReports() {
        List<ScamReportDTO> reports = service.getAllReports();

        return ResponseEntity.ok(
                ApiResponse.<List<ScamReportDTO>>builder()
                        .message("Lấy danh sách báo cáo thành công")
                        .data(reports)
                        .build()
        );
    }

    // -------------------- GET BY ID --------------------
    @Operation(
            summary = "Lấy báo cáo theo ID",
            description = "Dùng ID để lấy thông tin chi tiết của một báo cáo"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScamReportDTO>> getReport(@PathVariable Long id) {
        ScamReportDTO dto = service.getReportById(id);

        return ResponseEntity.ok(
                ApiResponse.<ScamReportDTO>builder()
                        .message("Lấy báo cáo theo ID thành công")
                        .data(dto)
                        .build()
        );
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
    @PostMapping
    public ResponseEntity<ApiResponse<ScamReportDTO>> createReport(
            @Valid @RequestBody ScamReportDTO dto) {

        ScamReportDTO created = service.createReport(dto);

        return ResponseEntity.ok(
                ApiResponse.<ScamReportDTO>builder()
                        .message("Tạo báo cáo thành công")
                        .data(created)
                        .build()
        );
    }

    // -------------------- UPDATE STATUS --------------------
    @Operation(
            summary = "Cập nhật trạng thái xử lý báo cáo",
            description = "Admin cập nhật trạng thái báo cáo như: pending / approved / rejected"
    )
    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ScamReportDTO>> updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        ScamReportDTO updated = service.updateStatus(id, status);

        return ResponseEntity.ok(
                ApiResponse.<ScamReportDTO>builder()
                        .message("Cập nhật trạng thái thành công")
                        .data(updated)
                        .build()
        );
    }

    // -------------------- DELETE REPORT --------------------
    @Operation(
            summary = "Xóa báo cáo",
            description = "Xóa báo cáo khỏi hệ thống theo ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteReport(@PathVariable Long id) {
        service.deleteReport(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .message("Xóa báo cáo thành công")
                        .data(null)
                        .build()
        );
    }
}
