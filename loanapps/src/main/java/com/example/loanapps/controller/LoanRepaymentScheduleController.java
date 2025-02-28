package com.example.loanapps.controller;


import com.example.loanapps.entity.LoanRepaymentSchedule;
import com.example.loanapps.service.LoanRepaymentScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/repayment-schedules")
@RequiredArgsConstructor
public class LoanRepaymentScheduleController {

    private final LoanRepaymentScheduleService repaymentScheduleService;

    // 🔹 Get repayment schedule for a specific loan
    @GetMapping("/{loanId}")
    public ResponseEntity<List<LoanRepaymentSchedule>> getRepaymentSchedule(@PathVariable Long loanId) {
        List<LoanRepaymentSchedule> schedule = repaymentScheduleService.getRepaymentScheduleByLoanId(loanId);
        return ResponseEntity.ok(schedule);
    }
}
