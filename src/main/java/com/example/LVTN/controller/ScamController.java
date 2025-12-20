package com.example.LVTN.controller;

import com.example.LVTN.dto.Requests.ScamPredictRequest;
import com.example.LVTN.dto.Response.ScamPredictResponse;
import com.example.LVTN.service.ScamDetectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scam")
public class ScamController {

    private final ScamDetectionService scamDetectionService;

    public ScamController(ScamDetectionService scamDetectionService) {
        this.scamDetectionService = scamDetectionService;
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkScam(@RequestBody ScamPredictRequest request) {

        ScamPredictResponse result =
                scamDetectionService.predict(request.getText());

        // Threshold logic
        if (result.getConfidence() < 0.6) {
            result.setLabel("nghi ngờ");
        }

        return ResponseEntity.ok(result);
    }
}

