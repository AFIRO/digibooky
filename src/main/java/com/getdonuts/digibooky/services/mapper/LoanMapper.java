package com.getdonuts.digibooky.services.mapper;

import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.domain.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    /*public CreateLoanDto toDto(Loan loan) {
        return new CreateLoanDto().setLoanId(loan.getLoanId()).setUserId(loan.getUserId())
                .setIsbn(loan.getIsbn()).setLendingDate(loan.getLendingDate())
                .setDueDate(loan.getDueDate());
    }*/

    public Loan toLoan(CreateLoanDto createLoanDto) {
        return new Loan(createLoanDto.getUserId(), createLoanDto.getIsbn(), createLoanDto.getDueDate());
    }
}
