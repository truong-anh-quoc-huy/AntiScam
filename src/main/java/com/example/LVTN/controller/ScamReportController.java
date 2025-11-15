package com.example.LVTN.controller;

import com.example.LVTN.dto.CheckScamResponse;
import com.example.LVTN.dto.ScamReportDTO;
import com.example.LVTN.service.ScamReportService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ScamReportController {

    private final ScamReportService service;

    public ScamReportController(ScamReportService service) {
        this.service = service;
    }

    @GetMapping
    public List<ScamReportDTO> getAllReports() {
        return service.getAllReports();
    }

    @GetMapping("/{id}")
    public ScamReportDTO getReport(@PathVariable Long id) {
        return service.getReportById(id);
    }

    @GetMapping("/check")
    public CheckScamResponse checkScam(@RequestParam String phone) {
        return service.checkScam(phone);
    }


    @PostMapping
    public ScamReportDTO createReport(@Valid @RequestBody ScamReportDTO dto) {
        return service.createReport(dto);
    }

    @PutMapping("/{id}/status")
    public ScamReportDTO updateStatus(@PathVariable Long id, @RequestParam String status) {
        return service.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteReport(@PathVariable Long id) {
        service.deleteReport(id);
    }



}
