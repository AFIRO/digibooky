package com.getdonuts.digibooky.api;

import com.getdonuts.digibooky.api.dto.CreateLoanDto;
import com.getdonuts.digibooky.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.getdonuts.digibooky.domain.Loan;
@RestController
@RequestMapping(path = "/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Loan createBook(@RequestBody CreateLoanDto createLoanDto) {
        return loanService.lendBook(createLoanDto);
    }
}
