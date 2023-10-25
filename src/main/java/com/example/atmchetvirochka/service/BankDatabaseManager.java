package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dao.AccountDAO;
import com.example.atmchetvirochka.model.dao.CardDAO;
import com.example.atmchetvirochka.model.dao.TransactionDAO;

public class BankDatabaseManager {
    public TransactionDAO transactionDAO;
    public CardDAO cardDAO;
    public AccountDAO accountDAO;
    public BankDatabaseManager(){
        transactionDAO = new TransactionDAO();
        cardDAO = new CardDAO();
        accountDAO = new AccountDAO();
    }
}
