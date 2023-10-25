package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;

import javax.lang.model.type.NullType;

public class SimpleConnnectorService implements ConnectorService{
    @Override
    public ResponseInfo<NullType> sendMoneyByCardNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo) {
        return null;
    }

    @Override
    public ResponseInfo<NullType> sendMoneyByPhoneNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo) {
        return null;
    }

    @Override
    public ResponseInfo<NullType> withdrawMoney(LoginInfo loginInfo, int amount) {
        return null;
    }

    @Override
    public ResponseInfo<Boolean> authorize(LoginInfo loginInfo) {
        return null;
    }

    @Override
    public int getBalance(LoginInfo loginInfo) {
        return 0;
    }

    @Override
    public ResponseInfo<CardDTO> getCardInformation(LoginInfo loginInfo) {
        return null;
    }

    @Override
    public ResponseInfo<AccountDTO> getAccountInformation(LoginInfo loginInfo) {
        return null;
    }
}
