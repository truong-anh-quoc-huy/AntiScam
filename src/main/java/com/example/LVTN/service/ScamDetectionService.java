package com.example.LVTN.service;

import com.example.LVTN.dto.Requests.ScamPredictRequest;
import com.example.LVTN.dto.Response.ScamPredictResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ScamDetectionService {

    private final RestTemplate restTemplate;

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    public ScamDetectionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ScamPredictResponse predict(String text) {
        ScamPredictRequest request = new ScamPredictRequest();
        request.setText(text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ScamPredictRequest> entity =
                new HttpEntity<>(request, headers);

        ResponseEntity<ScamPredictResponse> response =
                restTemplate.postForEntity(
                        aiServiceUrl + "/predict",
                        entity,
                        ScamPredictResponse.class
                );

        return response.getBody();
    }
}

