package com.example.loanapps.service;


import com.example.loanapps.dto.LoanDTO;
import com.example.loanapps.entity.Customer;
import com.example.loanapps.entity.Loan;
import com.example.loanapps.entity.LoanRepaymentSchedule;
import com.example.loanapps.repository.CustomerRepository;
import com.example.loanapps.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final LoanRepaymentScheduleService repaymentScheduleService; // Added Service

    @Transactional
    public LoanDTO giveLoan(LoanDTO loanDTO) {
        Customer customer = customerRepository.findById(loanDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        LocalDate endDate = calculateEndDate(loanDTO.getStartDate(), loanDTO.getRepaymentPeriod(), loanDTO.getFrequency());

        Loan loan = Loan.builder()
                .customer(customer)
                .principalAmount(loanDTO.getPrincipalAmount())
                .interestRate(loanDTO.getInterestRate())
                .repaymentPeriod(loanDTO.getRepaymentPeriod())
                .frequency(loanDTO.getFrequency())
                .startDate(loanDTO.getStartDate())
                .endDate(endDate)
                .build();

        loan = loanRepository.save(loan);


        List<LoanRepaymentSchedule> repaymentSchedule = repaymentScheduleService.generateRepaymentSchedule(loan);
        loan.setRepaymentSchedule(repaymentSchedule);

        return mapToDTO(loan);
    }

    public List<LoanDTO> getLoansByCustomer(Long customerId) {
        return loanRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private LoanDTO mapToDTO(Loan loan) {
        LoanDTO dto = new LoanDTO();
        dto.setCustomerId(loan.getCustomer().getId());
        dto.setPrincipalAmount(loan.getPrincipalAmount());
        dto.setInterestRate(loan.getInterestRate());
        dto.setRepaymentPeriod(loan.getRepaymentPeriod());
        dto.setFrequency(loan.getFrequency());
        dto.setStartDate(loan.getStartDate());
        return dto;
    }

    private LocalDate calculateEndDate(LocalDate startDate, int period, Loan.RepaymentFrequency frequency) {
        switch (frequency) {
            case WEEKLY: return startDate.plusWeeks(period);
            case MONTHLY: return startDate.plusMonths(period);
            case YEARLY: return startDate.plusYears(period);
            default: throw new IllegalArgumentException("Invalid repayment frequency");
        }
    }
}
