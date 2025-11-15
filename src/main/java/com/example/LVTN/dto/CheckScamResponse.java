package com.example.LVTN.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckScamResponse {
    private String phone;
    private boolean reported;
    private long count;
    String status;         // SUSPECT / VERIFIED / SAFE
    private String lastReport;

}