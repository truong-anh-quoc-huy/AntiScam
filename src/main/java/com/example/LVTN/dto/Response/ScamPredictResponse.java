package com.example.LVTN.dto.Response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ScamPredictResponse {
    private String label;
    private double confidence;
}
