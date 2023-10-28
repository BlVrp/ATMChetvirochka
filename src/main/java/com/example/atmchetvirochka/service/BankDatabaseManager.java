package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dao.AccountDAO;
import com.example.atmchetvirochka.model.dao.CardDAO;
import com.example.atmchetvirochka.model.dao.TransactionDAO;

public class BankDatabaseManager {
    private TransactionDAO transactionDAO;
    private CardDAO cardDAO;
    private AccountDAO accountDAO;

    public AccountDAO getAccountDAO(){
        return accountDAO;
    }
    public CardDAO getCardDAO(){
        return cardDAO;
    }
    public TransactionDAO getTransactionDAO(){
        return transactionDAO;
    }
    public BankDatabaseManager(){
        transactionDAO = new TransactionDAO();
        cardDAO = new CardDAO();
        accountDAO = new AccountDAO();
    }
}
