package com.getdonuts.digibooky.repository;

import com.getdonuts.digibooky.domain.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// CODEREVIEW Cleanup unused code
@Repository
public class LoanArchiveRepository {

    private final ConcurrentHashMap<String, Loan> archivedLoansByLoanId;

    @Autowired
    public LoanArchiveRepository() {
        this.archivedLoansByLoanId = new ConcurrentHashMap<>();
    }


    public Loan addLoanToArchive(Loan loan) {

       /* if(bookRepository.getBook(loan.getIsbn()).isPassive()){
            throw new IllegalArgumentException("The library does not contain book: " + loan.getIsbn());
        }

        if(archivedLoansByLoanId.containsKey(loan.getLoanId())){
            throw new IllegalArgumentException("Book already rented");
        }*/
        archivedLoansByLoanId.put(loan.getLoanId(), loan);
        return loan;
    }

    /*
    public Loan getLoan(String loanId){
        return archivedLoansByLoanId.get(loanId);
    }

     */
    public Collection<Loan> getAllArchivedLoansByUser(String userId) {
        return archivedLoansByLoanId.values().stream()
                .filter(loan -> loan.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    /*
    public boolean containsKey(String loanId) {
        return archivedLoansByLoanId.containsKey(loanId);
    }

     */

}
