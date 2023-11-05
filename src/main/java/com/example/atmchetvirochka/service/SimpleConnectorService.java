package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.RequestInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;

import javax.lang.model.type.NullType;
import java.util.Map;

public class SimpleConnectorService implements ConnectorService{
    Bank bank;
    String ATMid;

    Cypherator cypherator;

    public SimpleConnectorService(Bank bank, String ATMid, Cypherator cypherator){
        this.cypherator = cypherator;
        this.bank = bank;
        this.ATMid = ATMid;
    }
    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<NullType> sendMoneyByCardNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo) {
        Map<String, Object>args = loginInfo.toMap();
        args.putAll(transactionInputInfo.toMap());
        return (ResponseInfo<NullType>) bank.handleRequest(new RequestInfo(ATMid, cypherator.cypher(args), RequestInfo.RequestType.SEND_BY_CARD_NUM));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<NullType> sendMoneyByPhoneNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo) {
        Map<String, Object>args = loginInfo.toMap();
        args.putAll(transactionInputInfo.toMap());
        return (ResponseInfo<NullType>) bank.handleRequest(new RequestInfo(ATMid, cypherator.cypher(args), RequestInfo.RequestType.SEND_BY_PHONE_NUM));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<NullType> withdrawMoney(LoginInfo loginInfo, int amount) {
        Map<String, Object>args = loginInfo.toMap();
        args.put("amount", amount);
        RequestInfo requestInfo = new RequestInfo(ATMid, cypherator.cypher(args), RequestInfo.RequestType.WITHDRAW);
        return (ResponseInfo<NullType>) bank.handleRequest(requestInfo);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<Boolean> authorize(LoginInfo loginInfo) {
        return (ResponseInfo<Boolean>) bank.handleRequest(new RequestInfo(ATMid, cypherator.cypher(loginInfo.toMap()), RequestInfo.RequestType.AUTHORIZE));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<Long> getBalance(LoginInfo loginInfo) {
        return ((ResponseInfo<Long>)bank.handleRequest(new RequestInfo(ATMid, cypherator.cypher(loginInfo.toMap()), RequestInfo.RequestType.GET_BALANCE)));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<CardDTO> getCardInformation(LoginInfo loginInfo) {
        return (ResponseInfo<CardDTO>)bank.handleRequest(new RequestInfo(ATMid, cypherator.cypher(loginInfo.toMap()), RequestInfo.RequestType.GET_CARD_INFO));
    }

    @Override
    @SuppressWarnings("unchecked")
    public ResponseInfo<AccountDTO> getAccountInformation(LoginInfo loginInfo) {
        return (ResponseInfo<AccountDTO>)bank.handleRequest(new RequestInfo(ATMid, cypherator.cypher(loginInfo.toMap()), RequestInfo.RequestType.GET_PERSONAL_INFO));
    }
}