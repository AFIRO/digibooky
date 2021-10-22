package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.domain.Loan;
import com.getdonuts.digibooky.repository.BookRepository;
import com.getdonuts.digibooky.repository.LoanRepository;
import com.getdonuts.digibooky.services.mapper.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BookService bookservice;
    private final UserService userService;


    @Autowired
    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper, BookService bookservice, UserService userService) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.bookservice = bookservice;
        this.userService = userService;
    }

    public Loan lendBook(CreateLoanDto createLoanDto) {
        return loanRepository.createLoan(loanMapper.toLoan(createLoanDto));
    }

    // check if book is in repo => bookservice.getBook
    /*public boolean exist(String isbn){
        return (int) getAllBooks().stream()
                .filter(book -> book.getISBN().equals(isbn))
                .count() == 1;
    }*/
    // check if book is available
        
    // check if user exist

    //
}
