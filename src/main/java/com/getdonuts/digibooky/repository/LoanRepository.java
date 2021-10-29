package com.getdonuts.digibooky.repository;

import com.getdonuts.digibooky.domain.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class LoanRepository {

    private final ConcurrentHashMap<String, Loan> loansByLoanId;
    private final BookRepository bookRepository;
    private final LoanArchiveRepository loanArchiveRepository;

    @Autowired
    public LoanRepository(BookRepository bookRepository, LoanArchiveRepository loanArchiveRepository) {
        this.bookRepository = bookRepository;
        this.loanArchiveRepository = loanArchiveRepository;
        this.loansByLoanId = new ConcurrentHashMap<>();
        loansByLoanId.put("1A", new Loan("11", "A"));
        loansByLoanId.put("1B", new Loan("12", "AB"));
        loansByLoanId.put("1C", new Loan("13", "ABC"));
        loansByLoanId.put("1D", new Loan("14", "ABCDE"));
        loansByLoanId.put("1E", new Loan("15", "ABCDEF"));
    }

    public Loan createLoan(Loan loan) {

        if (bookRepository.getBook(loan.getIsbn()).isPassive()) {
            throw new IllegalArgumentException("The library does not contain book: " + loan.getIsbn());
        }

        if (loansByLoanId.containsKey(loan.getLoanId())) {
            throw new IllegalArgumentException("Book already rented");
        }
        loansByLoanId.put(loan.getLoanId(), loan);
        return loan;
    }

    public Loan returnLoan(String loanId) {
        Loan loanToTransfer = loansByLoanId.get(loanId);
        loanArchiveRepository.addLoanToArchive(loanToTransfer);
        return loansByLoanId.remove(loanId);
    }

    public Collection<Loan> getAllLoansByUser(String userId) {
        return loansByLoanId.values().stream()
                .filter(loan -> loan.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public boolean containsKey(String loanId) {
        // CODEREVIEW `containsKey` is not a descriptive name
        // what about `containsLoan`, `loanExists`, `isValidLoan`
        return loansByLoanId.containsKey(loanId);
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(loansByLoanId.values());
    }

    // CODEREVIEW
    // This is the only place in your code where you return an optional
    // There is no need, and it is not correctly used in the service layer
    public Optional<Loan> getLoanByIsbn(String isbn) {
        return loansByLoanId.values().stream()
                .filter(loan -> loan.getIsbn().equals(isbn))
                //.findFirst();
                .findAny();
        // CODEREVIEW This is just a small detail :D
        // The order is not relevant
    }
}
