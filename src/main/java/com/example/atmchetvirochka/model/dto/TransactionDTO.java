package com.example.atmchetvirochka.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionDTO {
    public final long id;
    public final String from;
    public final String to;
    public final LocalDate transaction_date;
    public final LocalTime transaction_time;
    public final long amount;
    public TransactionDTO(long id, String from, String to, LocalDate transaction_date, LocalTime transaction_time, long amount){
        this.id = id;
        this.from = from;
        this.to = to;
        this.transaction_date = transaction_date;
        this.transaction_time = transaction_time;
        this.amount = amount;
    }
    public TransactionDTO(String from, String to, long amount){
        this(0, from, to, LocalDate.now(), LocalTime.now(), amount);
    }
}
