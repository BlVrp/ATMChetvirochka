package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.RequestInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;

import javax.lang.model.type.NullType;

public class Bank {
    private BankDatabaseManager bankDatabaseManager;

    public Bank(){
        bankDatabaseManager = new BankDatabaseManager();
    }

    private boolean authorize(LoginInfo loginInfo){
        //placeholder
        return false;
    }

    public ResponseInfo<AccountDTO> getPersonalInformation(LoginInfo loginInfo){
        //placeholder
        return null;
    }

    public ResponseInfo<CardDTO> getCardInformation(LoginInfo loginInfo){
        //placeholder
        return null;
    }

    public ResponseInfo<Integer> getBalance(LoginInfo loginInfo){
        //placeholder
        return null;
    }

    public ResponseInfo<NullType> sendMoneyByCardNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo){
        //placeholder
        return null;
    }

    public ResponseInfo<NullType> withdrawMoney(LoginInfo loginInfo, int moneyAmount){
        //placeholder
        return null;
    }

    public ResponseInfo<NullType> sendMoneyByPhoneNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo){
        //placeholder
        return null;
    }

    public ResponseInfo handleRequest(RequestInfo requestInfo){
        //placeholder
        return null;
    }
}
