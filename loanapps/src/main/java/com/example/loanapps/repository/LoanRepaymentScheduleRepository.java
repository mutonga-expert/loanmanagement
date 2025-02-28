
package com.example.loanapps.repository;

import com.example.loanapps.entity.LoanRepaymentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LoanRepaymentScheduleRepository extends JpaRepository<LoanRepaymentSchedule, Long> {
    List<LoanRepaymentSchedule> findByLoanId(Long loanId);

    // 🔹 Fetch total paid loan amount
    @Query("SELECT COALESCE(SUM(r.paidAmount), 0) FROM LoanRepaymentSchedule r")
    BigDecimal getTotalPaidAmount();

    // 🔹 Fetch total outstanding loan balance
    @Query("SELECT COALESCE(SUM(r.balanceDue), 0) FROM LoanRepaymentSchedule r WHERE r.isPaid = false")
    BigDecimal getTotalOutstandingBalance();

}
