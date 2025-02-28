package com.example.loanapps.service;

import com.example.loanapps.entity.Loan;
import com.example.loanapps.entity.LoanRepaymentSchedule;
import com.example.loanapps.repository.LoanRepaymentScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanRepaymentScheduleService {

    private final LoanRepaymentScheduleRepository scheduleRepository;

    public LoanRepaymentScheduleService(LoanRepaymentScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public List<LoanRepaymentSchedule> generateRepaymentSchedule(Loan loan) {
        List<LoanRepaymentSchedule> schedule = new ArrayList<>();

        BigDecimal totalAmount = loan.getPrincipalAmount()
                .add(loan.getPrincipalAmount().multiply(loan.getInterestRate().divide(BigDecimal.valueOf(100))));
        int periods = loan.getRepaymentPeriod();
        BigDecimal installmentAmount = totalAmount.divide(BigDecimal.valueOf(periods), 2, BigDecimal.ROUND_HALF_UP);
        LocalDate dueDate = loan.getStartDate();
        BigDecimal totalBalance = totalAmount;
        for (int i = 0; i < periods; i++) {
            dueDate = getNextDueDate(dueDate, loan.getFrequency());

            LoanRepaymentSchedule installment = LoanRepaymentSchedule.builder()
                    .loan(loan)
                    .dueDate(dueDate)
                    .appliedAmount(installmentAmount)
                    .paidAmount(BigDecimal.ZERO)
                    .balanceDue(installmentAmount)
                    .totalBalance(totalBalance)
                    .isPaid(false)
                    .build();

            schedule.add(installment);


            totalBalance = totalBalance.subtract(installmentAmount);
        }

        return scheduleRepository.saveAll(schedule);
    }



    public List<LoanRepaymentSchedule> getRepaymentScheduleByLoanId(Long loanId) {
        return scheduleRepository.findByLoanId(loanId);
    }


    private LocalDate getNextDueDate(LocalDate current, Loan.RepaymentFrequency frequency) {
        switch (frequency) {
            case WEEKLY:
                return current.plusWeeks(1);
            case MONTHLY:
                return current.plusMonths(1);
            case YEARLY:
                return current.plusYears(1);
            default:
                throw new IllegalArgumentException("Invalid repayment frequency: " + frequency);
        }
    }
}


