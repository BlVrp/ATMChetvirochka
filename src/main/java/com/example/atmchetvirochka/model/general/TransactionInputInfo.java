package com.example.atmchetvirochka.model.general;

public class TransactionInputInfo {
    public final String card_number;
    public final int amount;
    public final String phone_number;
    public TransactionInputInfo(String card_number, int amount, String phone_number){
        this.card_number = card_number;
        this.amount = amount;
        this.phone_number = phone_number;
    }
}