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
import java.util.Objects;
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

    private String normalizePhone(String phone) {
        if (phone == null) return null;

        phone = phone.trim();

        // +84988xxxxxx → 0988xxxxxx
        if (phone.startsWith("+84")) {
            return "0" + phone.substring(3);
        }

        // 84988xxxxxx → 0988xxxxxx
        if (phone.startsWith("84")) {
            return "0" + phone.substring(2);
        }

        // 0988xxxxxx → OK
        return phone;
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


    public ScamReportResponse createReport(ScamReportRequest dto) {

        String reporterPhone = normalizePhone(dto.getReporterPhone());
        String phone = normalizePhone(dto.getPhone());

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        int todayCount = repository
                .countByReporterPhoneAndCreatedAtBetween(
                        reporterPhone, startOfDay, endOfDay
                );

        if (todayCount >= DAILY_LIMIT) {
            throw new RuntimeException(
                    "Bạn chỉ được gửi tối đa " + DAILY_LIMIT + " báo cáo mỗi ngày"
            );
        }

        ScamReportEntity entity = mapper.toEntity(dto);
        entity.setReporterPhone(reporterPhone);
        entity.setPhone(phone);
        entity.setStatus("PENDING");
        entity.setCreatedAt(LocalDateTime.now());

        ScamReportEntity saved = repository.save(entity);

        if (phone != null && !phone.isBlank()) {
            updateScamNumber(phone);
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

        if (scamNumber.getReportCount() >= 5) {
            scamNumber.setStatus("VERIFIED");
        } else {
            scamNumber.setStatus("SUSPECT");
        }

        scamNumberRepo.save(scamNumber);
    }

    //Check history
    // ===================== REPORT HISTORY =====================

    public List<ScamReportResponse> getReportsByReporterPhone(String reporterPhone) {
        String normalized = normalizePhone(reporterPhone);

        return repository
                .findByReporterPhoneOrderByCreatedAtDesc(normalized)
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }




    // ===================== API CHECK SCAM =====================

    public CheckScamResponse checkScam(String phone) {
        phone = normalizePhone(phone);
        ScamNumberEntity scamNumber = scamNumberRepo.findByPhone(phone).orElse(null);
        boolean isScam = false;
        boolean reported = scamNumber != null;
        long count = reported ? scamNumber.getReportCount() : 0;
        String lastReport = reported && scamNumber.getLastReportAt() != null
                ? scamNumber.getLastReportAt().toString()
                : null;
        String status = reported ? scamNumber.getStatus() : "SUSPECT";
        if(Objects.equals(status, "VERIFIED")) {
            isScam = true;
        }
        return CheckScamResponse.builder()
                .phone(phone)
                .reported(reported)
                .count(count)
                .lastReport(lastReport)
                .status(status)
                .isScam(isScam)
                .build();
    }
}
