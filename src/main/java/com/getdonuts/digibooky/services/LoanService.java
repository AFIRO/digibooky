package com.getdonuts.digibooky.services;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.BookWithDetailsDto;
import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.api.dto.ReturnLoanDto;
import com.getdonuts.digibooky.domain.Loan;
import com.getdonuts.digibooky.domain.User;
import com.getdonuts.digibooky.exceptions.AuthorisationException;
import com.getdonuts.digibooky.repository.LoanArchiveRepository;
import com.getdonuts.digibooky.repository.LoanRepository;
import com.getdonuts.digibooky.services.mapper.BookMapper;
import com.getdonuts.digibooky.services.mapper.LoanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanArchiveRepository loanArchiveRepository;
    private final LoanMapper loanMapper;
    private final BookMapper bookMapper;
    private final BookService bookService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(LoanService.class);


    @Autowired
    public LoanService(LoanRepository loanRepository, LoanArchiveRepository loanArchiveRepository, LoanMapper loanMapper, BookMapper bookMapper, BookService bookService, UserService userService) {
        this.loanRepository = loanRepository;
        this.loanArchiveRepository = loanArchiveRepository;
        this.loanMapper = loanMapper;
        this.bookMapper = bookMapper;
        this.bookService = bookService;
        this.userService = userService;
    }

    public BookWithDetailsDto getBookWithDetails(String isbn) {

        if(!bookService.exists(isbn)){
            throw new IllegalArgumentException("Book ISBN: " + isbn + " does not exist");
        }

        Optional<Loan> loan = loanRepository.getLoanByIsbn(isbn);

        if(loan.isPresent()){
            User user = userService.getUserById(loan.get().getUserId());
            String userFullName = user.getFirstName() + " " + user.getLastName();
            return bookMapper.mapToBookWithDetailsDto(bookService.getBookFromRepo(isbn)).setFullName(userFullName);
        }
        return bookMapper.mapToBookWithDetailsDto(bookService.getBookFromRepo(isbn));
    }


    public Loan lendBook(CreateLoanDto createLoanDto) {
        if(isValidLoan(createLoanDto)) {
            bookService.getBookFromRepo(createLoanDto.getIsbn()).setLent(true);
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
        String message = "Returned on time.";
        if(userService.validateMember(userId) && assertUserExists(userId)){
            if(checkIfLate(loanToBeReturned)){
                message = generateMessage(loanToBeReturned.getDueDate());
            }
            makeBookNotLent(loanToBeReturned.getIsbn());

            return loanMapper.toReturnLoanDto(loanToBeReturned).setMessage(message);
        }
        return loanMapper.toReturnLoanDto(loanToBeReturned).setMessage(message);
    }

    public List<BookDto> getLentBooksByUser(String librarianId, String userId) {
        assertLibrarianIsValid(librarianId);
        assertUserExists(userId);

        List<Loan> loansByUserFromLoanRepository = new ArrayList<>(loanRepository.getAllLoansByUser(userId));
        List<Loan> loansByUserFromArchivedLoanRepository = new ArrayList<>(loanArchiveRepository.getAllArchivedLoansByUser(userId));

        loansByUserFromLoanRepository.addAll(loansByUserFromArchivedLoanRepository);

        return loansByUserFromLoanRepository.stream()
                .map(Loan::getIsbn)
                .map(bookService::getBook)
                .map(bookMapper::summaryBookDtoToBookDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getOverdueBooks(String librarianId) {
        assertLibrarianIsValid(librarianId);
        List<Loan> loans = loanRepository.getAllLoans();
        return loans.stream()
                .filter(this::checkIfLate)
                .map(Loan::getIsbn)
                .map(bookService::getBook)
                .map(bookMapper::summaryBookDtoToBookDto)
                .collect(Collectors.toList());
    }

    private void assertLibrarianIsValid(String librarianId) {
        if (!userService.validateLibrarian(librarianId)) {
            throw new AuthorisationException();
        }
    }

    public boolean assertUserExists(String userId){
        if(!userService.userExists(userId)){
            throw new IllegalArgumentException("Member with ID : " + userId + " doesn't exist");
        }
        return true;
    }

    private void makeBookNotLent(String isbn) {
        bookService.getBookFromRepo(isbn).setLent(false);
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
        return bookExists(isbn) && isNotLent(isbn) && assertUserExists(userid) && hasRightToLoan(userid);
    }

    public boolean bookExists(String isbn) {
        if(!bookService.exists(isbn)){
            throw new IllegalArgumentException("Book with isbn : " + isbn + " doesn't exist");
        }
        return true;
    }

    public boolean isNotLent(String isbn){
        if(bookService.getBook(isbn).isLent()){
            throw new IllegalArgumentException("Book with isbn : " + isbn + " is already lent");
        }
        return true;
    }

    public boolean hasRightToLoan(String userId){
        if(!userService.validateMember(userId)){
            throw new IllegalArgumentException("User with ID : " + userId + " doesn't has right to loan a book.");
        }
        return true;
    }

    public Loan getBookByIsbn(String isbn) {
        return loanRepository.getLoanByIsbn(isbn).get();
    }
}
