package com.example.loanapps.entity;



import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRepaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal appliedAmount;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal paidAmount = BigDecimal.ZERO;
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal balanceDue;     //
    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal totalBalance;   //

    @Column(nullable = false)
    private boolean isPaid = false;


    public LoanRepaymentSchedule(Loan loan, LocalDate dueDate, BigDecimal appliedAmount, boolean isPaid) {
        this.loan = loan;
        this.dueDate = dueDate;
        this.appliedAmount = appliedAmount;
        this.paidAmount = BigDecimal.ZERO;
        this.balanceDue = appliedAmount;
        this.totalBalance = loan.getPrincipalAmount()
                .add(loan.getPrincipalAmount().multiply(loan.getInterestRate().divide(BigDecimal.valueOf(100))));
        this.isPaid = isPaid;
    }

    // 🔹 Method to handle payments
    public void makePayment(BigDecimal payment) {
        if (payment.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Payment must be greater than zero");
        }

        if (payment.compareTo(this.balanceDue) > 0) {
            throw new IllegalArgumentException("Payment cannot exceed balance due");
        }

        this.paidAmount = this.paidAmount.add(payment);
        this.balanceDue = this.appliedAmount.subtract(this.paidAmount);
        this.totalBalance = this.totalBalance.subtract(payment);

        if (this.balanceDue.compareTo(BigDecimal.ZERO) <= 0) {
            this.isPaid = true;
        }
    }
}
