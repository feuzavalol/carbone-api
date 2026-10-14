package org.example.controller;

import org.example.model.responses.SummaryDTO;
import org.example.service.SummaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class SummaryController {
    private final SummaryService service;

    public SummaryController(SummaryService summaryService) {
        this.service = summaryService;
    }

    @GetMapping("/foodSummary")
    public ResponseEntity<Map<String, SummaryDTO>> findAllFoodItem(
            @RequestParam UUID committeeId
    ) {
        SummaryDTO result = service.getFoodSummary(committeeId);
        Map<String, SummaryDTO> success = Map.of("success", result);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/mealSummary")
    public ResponseEntity<Map<String, SummaryDTO>> fetchMealSummary(
            @RequestParam UUID committeeId
    ) {
        SummaryDTO result = service.getMealSummary(committeeId);
        Map<String, SummaryDTO> success = Map.of("success", result);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/goodSummary")
    public ResponseEntity<Map<String, SummaryDTO>> fetchGoodSummary(
            @RequestParam UUID committeeId
    ) {
        SummaryDTO result = service.getGoodsSummary(committeeId);
        Map<String, SummaryDTO> success = Map.of("success", result);
        return ResponseEntity.ok(success);
    }
}
