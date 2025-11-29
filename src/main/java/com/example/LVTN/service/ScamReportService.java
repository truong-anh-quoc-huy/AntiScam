package com.example.LVTN.service;

import com.example.LVTN.dto.Requests.ScamReportRequest;
import com.example.LVTN.dto.Response.CheckScamResponse;
import com.example.LVTN.dto.Response.ScamReportResponse;
import com.example.LVTN.entity.ScamNumberEntity;
import com.example.LVTN.entity.ScamReportEntity;
import com.example.LVTN.repository.ScamNumberRepository;
import com.example.LVTN.repository.ScamReportMapper;
import com.example.LVTN.repository.ScamReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScamReportService {

    private final ScamReportRepository repository;
    private final ScamReportMapper mapper;
    private final ScamNumberRepository scamNumberRepo;

    private static final int DAILY_LIMIT = 5;

    public ScamReportService(ScamReportRepository repository, ScamReportMapper mapper,
                             ScamNumberRepository scamNumberRepo) {
        this.repository = repository;
        this.mapper = mapper;
        this.scamNumberRepo = scamNumberRepo;
    }

    // ===================== CRUD REPORT =====================

    public List<ScamReportResponse> getAllReports() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ScamReportResponse getReportById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    public ScamReportResponse createReport(Long userId, ScamReportRequest dto) {

        // ===================== CHECK DAILY LIMIT =====================
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        int todayCount = repository.countByUserIdAndCreatedAtBetween(userId, startOfDay, endOfDay);
        if (todayCount >= DAILY_LIMIT) {
            throw new RuntimeException("Bạn chỉ được gửi tối đa "
                    + DAILY_LIMIT + " báo cáo mỗi ngày.");
        }

        // ===================== CREATE REPORT =========================
        ScamReportEntity entity = mapper.toEntity(dto);
        entity.setUserId(userId);
        entity.setStatus("PENDING");
        entity.setCreatedAt(LocalDateTime.now());

        ScamReportEntity saved = repository.save(entity);

        if (entity.getPhone() != null && !entity.getPhone().isBlank()) {
            updateScamNumber(entity.getPhone());
        }

        return mapper.toResponseDTO(saved);
    }

    public ScamReportResponse updateStatus(Long id, String status) {
        ScamReportEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        entity.setStatus(status);
        return mapper.toResponseDTO(repository.save(entity));
    }

    public void deleteReport(Long id) {
        repository.deleteById(id);
    }

    // ===================== UPDATE SCAM NUMBER =====================

    private void updateScamNumber(String phone) {
        ScamNumberEntity scamNumber = scamNumberRepo.findByPhone(phone)
                .orElseGet(() -> {
                    ScamNumberEntity newRecord = new ScamNumberEntity();
                    newRecord.setPhone(phone);
                    newRecord.setReportCount(0L);
                    newRecord.setStatus("SUSPECT");
                    return newRecord;
                });

        scamNumber.setReportCount(scamNumber.getReportCount() + 1);
        scamNumber.setLastReportAt(LocalDateTime.now());

        if (scamNumber.getReportCount() >= 10) {
            scamNumber.setStatus("VERIFIED");
        } else if (scamNumber.getReportCount() >= 5) {
            scamNumber.setStatus("AUTO_CONFIRMED");
        } else {
            scamNumber.setStatus("SUSPECT");
        }

        scamNumberRepo.save(scamNumber);
    }

    // ===================== API CHECK SCAM =====================

    public CheckScamResponse checkScam(String phone) {
        ScamNumberEntity scamNumber = scamNumberRepo.findByPhone(phone).orElse(null);

        boolean reported = scamNumber != null;
        long count = reported ? scamNumber.getReportCount() : 0;
        String lastReport = reported && scamNumber.getLastReportAt() != null
                ? scamNumber.getLastReportAt().toString()
                : null;
        String status = reported ? scamNumber.getStatus() : "SUSPECT";

        return CheckScamResponse.builder()
                .phone(phone)
                .reported(reported)
                .count(count)
                .lastReport(lastReport)
                .status(status)
                .build();
    }
}
