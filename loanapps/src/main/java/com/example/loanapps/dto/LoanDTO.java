package com.example.loanapps.dto;


import com.example.loanapps.entity.Loan.RepaymentFrequency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanDTO {
    private Long customerId;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private int repaymentPeriod;
    private RepaymentFrequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
}
