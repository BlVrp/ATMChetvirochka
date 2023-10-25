package com.example.atmchetvirochka.model.dto;

import java.time.LocalDateTime;

public class TransactionDTO {
    public final long id;
    public final String from;
    public final String to;
    public final LocalDateTime date_time;
    public final long amount;
    public TransactionDTO(long id, String from, String to, LocalDateTime date_time, long amount){
        this.id = id;
        this.from = from;
        this.to = to;
        this.date_time = date_time;
        this.amount = amount;
    }
}
