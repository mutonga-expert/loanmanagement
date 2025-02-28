
package com.example.loanapps.service;

import com.example.loanapps.repository.LoanRepository;
import com.example.loanapps.repository.LoanRepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoanStatisticsService {

    private final LoanRepository loanRepository;
    private final LoanRepaymentScheduleRepository repaymentScheduleRepository;

    public BigDecimal getTotalLoansDisbursed() {
        return loanRepository.getTotalDisbursedLoans();
    }

    public BigDecimal getTotalLoansPaid() {
        return repaymentScheduleRepository.getTotalPaidAmount();
    }

    public BigDecimal getOutstandingLoans() {
        return getTotalLoansDisbursed().subtract(getTotalLoansPaid());
    }


    public Map<String, BigDecimal> getLoanSummary() {
        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("Total Loans Disbursed", getTotalLoansDisbursed());
        summary.put("Total Loans Paid", getTotalLoansPaid());
        summary.put("Outstanding Loans", getOutstandingLoans());
        return summary;
    }
}
