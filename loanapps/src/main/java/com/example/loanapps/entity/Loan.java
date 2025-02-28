package com.example.loanapps.entity;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private int repaymentPeriod;

    @Enumerated(EnumType.STRING)
    private RepaymentFrequency frequency;

    private LocalDate startDate;
    private LocalDate endDate;

    // 🔹 Fix: Add List of Repayment Schedules
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanRepaymentSchedule> repaymentSchedule;

    // 🔹 Add setter for repayment schedule
    public void setRepaymentSchedule(List<LoanRepaymentSchedule> repaymentSchedule) {
        this.repaymentSchedule = repaymentSchedule;
    }

    public enum RepaymentFrequency {
        WEEKLY, MONTHLY, YEARLY
    }
}
