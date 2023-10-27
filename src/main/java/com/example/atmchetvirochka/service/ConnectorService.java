package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;

import javax.lang.model.type.NullType;

public interface ConnectorService {
    ResponseInfo<NullType> sendMoneyByCardNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo);
    ResponseInfo<NullType> sendMoneyByPhoneNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo);
    ResponseInfo<NullType> withdrawMoney(LoginInfo loginInfo, int amount);
    ResponseInfo<Boolean> authorize(LoginInfo loginInfo);
    long getBalance(LoginInfo loginInfo);
    ResponseInfo<CardDTO> getCardInformation(LoginInfo loginInfo);
    ResponseInfo<AccountDTO> getAccountInformation(LoginInfo loginInfo);
}
