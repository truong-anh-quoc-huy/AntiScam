package com.example.LVTN.dto.Requests;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ScamPredictRequest {
    private String text;
}
