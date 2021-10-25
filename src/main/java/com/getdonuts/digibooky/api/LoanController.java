package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.api.dto.ReturnLoanDto;
import com.getdonuts.digibooky.services.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.getdonuts.digibooky.domain.Loan;
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

    @DeleteMapping(produces = "application/json", path = "/{userId}/{loanId}/")
    @ResponseStatus(HttpStatus.OK)
    public ReturnLoanDto returnLoan(@PathVariable("userId") String userId,
                                    @PathVariable("loanId") String loanId){
        return loanService.returnLoan(userId, loanId);
    }

}
