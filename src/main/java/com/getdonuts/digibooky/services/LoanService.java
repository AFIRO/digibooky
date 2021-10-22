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
        String isbn = createLoanDto.getIsbn();
        if(bookExists(isbn) && isLent(isbn) && memberExists(createLoanDto.getUserId())) {
            return loanRepository.createLoan(loanMapper.toLoan(createLoanDto));
        }
        throw new IllegalArgumentException("Something went wrong...");
    }

    public boolean bookExists(String isbn) {
        if(!bookservice.exist(isbn)){
            throw new IllegalArgumentException("Book doesn't exist");
        }
        return true;
    }

    public boolean isLent(String isbn){
        if(!bookservice.getBook(isbn).isLent()){
            throw new IllegalArgumentException("Book is already lent");
        }
        return true;
    }

    public boolean memberExists(String memberId){
        if(!userService.memberExists(memberId)){
            throw new IllegalArgumentException("Member doesn't exist");
        }
        return true;
    }

}
