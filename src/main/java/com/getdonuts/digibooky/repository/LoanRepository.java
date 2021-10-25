package com.getdonuts.digibooky.repository;

import com.getdonuts.digibooky.domain.Loan;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LoanRepository {

    private final ConcurrentHashMap<String, Loan> loansByLoanId;
    private final BookRepository bookRepository;

    public LoanRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.loansByLoanId = new ConcurrentHashMap<>();
        loansByLoanId.put("1A", new Loan("11", "A"));
        loansByLoanId.put("1B", new Loan("12", "AB"));
        loansByLoanId.put("1C", new Loan("13", "ABC"));
        loansByLoanId.put("1D", new Loan("14", "ABCDE"));
        loansByLoanId.put("1E", new Loan("15", "ABCDEF"));
    }

    public Loan createLoan(Loan loan) {

        if(bookRepository.getBook(loan.getIsbn()).isPassive()){
            throw new IllegalArgumentException("The library does not contain book: " + loan.getIsbn());
        }

        if(loansByLoanId.containsKey(loan.getLoanId())){
            throw new IllegalArgumentException("Book already rented");
        }
        loansByLoanId.put(loan.getLoanId(), loan);
        return loan;
    }

    public Loan returnLoan(String loanId){
        return loansByLoanId.get(loanId);
    }

    public boolean containsKey(String loanId) {
        return loansByLoanId.containsKey(loanId);
    }
}
