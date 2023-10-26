package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.RequestInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;

import javax.lang.model.type.NullType;
import java.util.Map;

public class SimpleConnnectorService implements ConnectorService{
    Bank bank;
    String ATMid;

    Cypherator cypherator = new SimpleCypherator();

    Decypherator decypherator = new SimpleDecypherator();

    SimpleConnnectorService(Bank bank, String ATMid){
        this.bank = bank;
        this.ATMid = ATMid;
    }
    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<NullType> sendMoneyByCardNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo) {
        Map<String, Object>args = loginInfo.toMap();
        args.putAll(transactionInputInfo.toMap());
        return (ResponseInfo<NullType>) bank.handleRequest(new RequestInfo(ATMid, args, RequestInfo.RequestType.SEND_BY_CARD_NUM));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<NullType> sendMoneyByPhoneNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo) {
        Map<String, Object>args = loginInfo.toMap();
        args.putAll(transactionInputInfo.toMap());
        return (ResponseInfo<NullType>) bank.handleRequest(new RequestInfo(ATMid, args, RequestInfo.RequestType.SEND_BY_PHONE_NUM));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<NullType> withdrawMoney(LoginInfo loginInfo, int amount) {
        if(amount<0){
            return new ResponseInfo<>(false, "Invalid amount of money", null);
        }
        Map<String, Object>args = loginInfo.toMap();
        args.put("amount", amount);
        RequestInfo requestInfo = new RequestInfo(ATMid, args, RequestInfo.RequestType.WITHDRAW);
        return (ResponseInfo<NullType>) bank.handleRequest(requestInfo);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<Boolean> authorize(LoginInfo loginInfo) {
        return (ResponseInfo<Boolean>) bank.handleRequest(new RequestInfo(ATMid, loginInfo.toMap(), RequestInfo.RequestType.AUTHORIZE));
    }

    @Override
    @SuppressWarnings("unchecked")
    public int getBalance(LoginInfo loginInfo) {
        return ((ResponseInfo<Integer>)bank.handleRequest(new RequestInfo(ATMid, loginInfo.toMap(), RequestInfo.RequestType.GET_BALANCE))).data;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<CardDTO> getCardInformation(LoginInfo loginInfo) {
        return (ResponseInfo<CardDTO>)bank.handleRequest(new RequestInfo(ATMid, loginInfo.toMap(), RequestInfo.RequestType.GET_CARD_INFO));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<AccountDTO> getAccountInformation(LoginInfo loginInfo) {
        return (ResponseInfo<AccountDTO>)bank.handleRequest(new RequestInfo(ATMid, loginInfo.toMap(), RequestInfo.RequestType.GET_PERSONAL_INFO));
    }
}
