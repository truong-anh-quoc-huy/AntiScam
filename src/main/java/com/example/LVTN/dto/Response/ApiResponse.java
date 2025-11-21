package com.example.LVTN.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private String message;
    private T data;
}
