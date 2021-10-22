package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.domain.Loan;
import com.getdonuts.digibooky.repository.LoanRepository;
import com.getdonuts.digibooky.services.mapper.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    @Autowired
    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    public Loan lendBook(CreateLoanDto createLoanDto) {
        return loanRepository.createLoan(loanMapper.toLoan(createLoanDto));
    }
}
