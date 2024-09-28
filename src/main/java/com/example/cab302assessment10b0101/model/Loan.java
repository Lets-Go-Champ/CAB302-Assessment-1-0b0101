package com.example.cab302assessment10b0101.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Loan {
    private final StringProperty borrower;
    private final ObjectProperty<Book> book;
    private final ObjectProperty<LocalDate> date;


    public Loan(String borrowerName, Book book, LocalDate date){
        this.book = new SimpleObjectProperty<>(this,"Book", book);
        this.borrower = new SimpleStringProperty(this, borrowerName);
        this.date = new SimpleObjectProperty<>(this,"Date", date);
    }

    public StringProperty getBorrower(){return this.borrower;}

    public ObjectProperty<LocalDate> getDate(){return this.date;}

    public ObjectProperty<Book> getBook(){return this.book;}
}
