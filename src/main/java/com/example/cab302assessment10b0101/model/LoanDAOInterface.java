package com.example.cab302assessment10b0101.model;

import java.util.List;


public interface LoanDAOInterface {

    /**
     * Inserts a new loan into the database.
     *
     * @param loan The Collection object to be inserted.
     */
    void insert(Loan loan);


    List<Loan> getAll();

}
