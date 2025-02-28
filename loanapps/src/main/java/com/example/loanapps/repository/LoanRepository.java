package com.example.loanapps.repository;




import com.example.loanapps.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByCustomerId(Long customerId);


    @Query("SELECT COALESCE(SUM(l.principalAmount), 0) FROM Loan l")
    BigDecimal getTotalDisbursedLoans();

    @Query("SELECT COALESCE(SUM(r.paidAmount), 0) FROM LoanRepaymentSchedule r WHERE r.isPaid = true")
    BigDecimal getTotalPaidLoans();
}
