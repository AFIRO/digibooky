package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.api.dto.ReturnLoanDto;
import com.getdonuts.digibooky.domain.Loan;
import com.getdonuts.digibooky.repository.LoanRepository;
import com.getdonuts.digibooky.services.mapper.LoanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BookService bookservice;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(LoanService.class);


    @Autowired
    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper, BookService bookservice, UserService userService) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.bookservice = bookservice;
        this.userService = userService;
    }


    public Loan lendBook(CreateLoanDto createLoanDto) {
        if(isValidLoan(createLoanDto)) {
            bookservice.getBookFromRepo(createLoanDto.getIsbn()).setLent(true);
            logger.info("Book " + createLoanDto.getIsbn() + " is lent by user " + createLoanDto.getUserId());
            return loanRepository.createLoan(loanMapper.toLoan(createLoanDto));
        }
        throw new IllegalArgumentException("Something went wrong...");
    }

    public ReturnLoanDto returnLoan(String userId, String loanId) {

        if (!isValidLoanId(loanId)){
            throw new IllegalArgumentException("loan Id: " + loanId + " is not valid");
        }

        Loan loanToBeReturned = loanRepository.returnLoan(loanId);
        String message = "";
        if(userService.validateMember(userId) && userExists(userId)){
            if(checkIfLate(loanToBeReturned)){
                message = generateMessage(loanToBeReturned.getDueDate());
            }
            makeBookNotLent(loanToBeReturned.getIsbn());
            return loanMapper.toReturnLoanDto(loanToBeReturned).setMessage(message);
        }
        return loanMapper.toReturnLoanDto(loanToBeReturned);
    }

    private void makeBookNotLent(String isbn) {
        bookservice.getBookFromRepo(isbn).setLent(false);
    }

    private boolean isValidLoanId(String loanId) {
        return loanRepository.containsKey(loanId);
    }

    private String generateMessage(LocalDate dueDate) {
        return "The book is returned " + ChronoUnit.DAYS.between(dueDate, LocalDate.now()) + " days late";
    }

    private boolean checkIfLate(Loan loanToBeReturned) {
        return loanToBeReturned.getDueDate().isBefore(LocalDate.now());
    }

    private boolean isValidLoan(CreateLoanDto createLoanDto) {
        String isbn = createLoanDto.getIsbn();
        String userid = createLoanDto.getUserId();
        return bookExists(isbn) && isNotLent(isbn) && userExists(userid) && hasRightToLoan(userid);
    }

    public boolean bookExists(String isbn) {
        if(!bookservice.exists(isbn)){
            throw new IllegalArgumentException("Book with isbn : " + isbn + " doesn't exist");
        }
        return true;
    }

    public boolean isNotLent(String isbn){
        if(bookservice.getBook(isbn).isLent()){
            throw new IllegalArgumentException("Book with isbn : " + isbn + " is already lent");
        }
        return true;
    }

    // TODO refactor into userService
    public boolean userExists(String userId){
        if(!userService.userExists(userId)){
            throw new IllegalArgumentException("Member with ID : " + userId + " doesn't exist");
        }
        return true;
    }

    public boolean hasRightToLoan(String userId){
        if(!userService.validateMember(userId)){
            throw new IllegalArgumentException("User with ID : " + userId + " doesn't has right to loan a book.");
        }
        return true;
    }


}
