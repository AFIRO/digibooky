package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.api.dto.BookDto;
import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.api.dto.ReturnLoanDto;
import com.getdonuts.digibooky.domain.Loan;
import com.getdonuts.digibooky.services.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/loans")
public class LoanController {

    private final LoanService loanService;
    private final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Loan createLoan(@RequestBody CreateLoanDto createLoanDto) {
        logger.info("lendBook() called");
        return loanService.lendBook(createLoanDto);
    }

    // CODEREVIEW a slightly more REST-like style is possible
    // DELETE /loans/{loadId}
    @DeleteMapping(produces = "application/json", path = "/{userId}/{loanId}/")
    @ResponseStatus(HttpStatus.OK)
    public ReturnLoanDto returnLoan(@PathVariable("userId") String userId,
                                    @PathVariable("loanId") String loanId) {
        logger.info("returnLoan() called");
        // CODEREVIEW What is the purpose of the userId? (discussed in service)
        return loanService.returnLoan(userId, loanId);
    }

    // CODEREVIEW a slightly more REST-like style is possible
    // GET /users/{userId}/loans (so in the user controller)
    // or GET /loans?user={userId}
    @GetMapping(produces = "application/json", path = "/{librarianId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getLentBooksByUser(@PathVariable("librarianId") String librarianId,
                                            @PathVariable("userId") String userId) {
        // CODEREVIEW Suggestion: add the librarianId to the headers with @RequestHeader
        logger.info("getLentBooksByUser() called");
        return loanService.getLentBooksByUser(librarianId, userId);
    }

    // CODEREVIEW a slightly more REST-like style is possible
    // GET /overdue
    // or GET /loans?overdue=true
    // or GET /loans/overdue (this is used in the wild, but it slightly violates the REST style)
    @GetMapping(produces = "application/json", path = "/overdue/{librarianId}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getOverdueBooks(@PathVariable("librarianId") String librarianId) {
        // CODEREVIEW Suggestion: add the librarianId to the headers with @RequestHeader
        logger.info("getOverdueBooks() called");
        return loanService.getOverdueBooks(librarianId);
    }
}
