package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.repository.LoanRepository;
import com.getdonuts.digibooky.services.mapper.BookMapper;
import com.getdonuts.digibooky.services.mapper.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BookMapper bookMapper;

    @Autowired
    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper, BookMapper bookMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.bookMapper = bookMapper;
    }

    public BookDto lendBook(CreateLoanDto createLoanDto) {
        if(createLoanDto.getDueDate() != null){
            return bookMapper.mapToDto(loanRepository.createLoan(loanMapper.toDtoWithDueDate(createLoanDto)));
        }
        return bookMapper.mapToDto(loanRepository.createLoan(loanMapper.toDto(createLoanDto)));
    }
}
