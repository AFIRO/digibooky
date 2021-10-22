package com.getdonuts.digibooky.services.mapper;

import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.domain.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanMapper {

    public Loan toLoan(CreateLoanDto createLoanDto) {
        return new Loan(createLoanDto.getUserId(), createLoanDto.getIsbn(), createLoanDto.getDueDate());
    }
}
