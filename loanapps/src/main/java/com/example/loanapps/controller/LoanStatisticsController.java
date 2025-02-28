package com.example.loanapps.controller;


import com.example.loanapps.service.LoanStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class LoanStatisticsController {

    private final LoanStatisticsService loanStatisticsService;

    // 🔹 Get total loans disbursed vs loans paid
    @GetMapping("/loans-summary")
    public ResponseEntity<Map<String, BigDecimal>> getLoanSummary() {
        Map<String, BigDecimal> summary = loanStatisticsService.getLoanSummary();
        return ResponseEntity.ok(summary);
    }
}
