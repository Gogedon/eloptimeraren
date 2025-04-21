package com.eloptimeraren.business_logic.api.controller;

import com.eloptimeraren.business_logic.api.request.AnalyzeRequest;
import com.eloptimeraren.business_logic.api.response.AnalysisResponse;
import com.eloptimeraren.business_logic.mapper.AnalysisResponseMapper;
import com.eloptimeraren.business_logic.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/optimize")
public class AnalyzerController {

    private final AnalysisService service;

    public AnalyzerController(AnalysisService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AnalysisResponse> analyze(@Valid @RequestBody final AnalyzeRequest requestBody) {
        var priceZone = requestBody.getPriceZone();
        var consumption = requestBody.getConsumption();
        return ResponseEntity.ok(
                AnalysisResponseMapper.mapToResponse(priceZone, service.analyze(priceZone, consumption)));
    }
}
