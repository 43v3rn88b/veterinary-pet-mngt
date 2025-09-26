package com.codecraft.veterinary_pet_system.controller;

import com.codecraft.veterinary_pet_system.dto.ReportDTO;
import com.codecraft.veterinary_pet_system.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<ReportDTO> getReport() {
        return ResponseEntity.ok(reportService.generateReport());
    }
}

