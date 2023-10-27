package com.example.atmchetvirochka.model.dto;

public class CardDTO {
    public final String card_number;
    public final String cvv;
    public final String full_name;
    public final int due_to_year;
    public final int due_to_month;
    public final String pin;
    public final long balance;
    public final long account_id;
    public final int attempts;
    public final int is_default;
    public CardDTO(String card_number, String cvv, String full_name, int due_to_year, int due_to_month, String pin, long balance, long account_id, int attempts, int is_default){
        this.card_number = card_number;
        this.cvv = cvv;
        this.full_name = full_name;
        this.due_to_year = due_to_year;
        this.due_to_month = due_to_month;
        this.pin = pin;
        this.balance = balance;
        this.account_id = account_id;
        this.attempts = attempts;
        this.is_default = is_default;
    }
}
