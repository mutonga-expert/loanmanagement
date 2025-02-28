package com.example.loanapps.controller;

import com.example.loanapps.dto.LoanDTO;
import com.example.loanapps.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:8085")
@Tag(name = "Loan Management", description = "Endpoints for loan operations")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping("/{customerId}/give")
    @Operation(summary = "Give loan to a customer")
    public ResponseEntity<LoanDTO> giveLoan(
            @PathVariable Long customerId,
            @RequestBody LoanDTO loanDTO) {
        return ResponseEntity.ok(loanService.giveLoan(loanDTO));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get loans by customer ID")
    public ResponseEntity<List<LoanDTO>> getLoansByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(loanService.getLoansByCustomer(customerId));
    }
}
