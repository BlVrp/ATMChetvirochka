package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.RequestInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.lang.model.type.NullType;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SimpleConnnectorServiceTest {
    private Bank bankMock;

    private Cypherator cypheratorMock;
    private SimpleConnnectorService service;

    private LoginInfo testLoginInfo;

    @BeforeEach
    void setUp() {
        testLoginInfo = new LoginInfo("testCard", "testPin");
        bankMock = mock(Bank.class);
        cypheratorMock = mock(Cypherator.class);
        when(cypheratorMock.cypher(testLoginInfo.toMap())).thenReturn("cyphered");
        service = new SimpleConnnectorService(bankMock, "TestATMid", cypheratorMock);
    }

    @Test
    void sendMoneyByCardNumber() {
        TransactionInputInfo transactionInputInfo = new TransactionInputInfo("2222", 23, null);
        ResponseInfo<NullType>mockResponse = new ResponseInfo<>(true, "okay", null);
        Map<String, Object> map = testLoginInfo.toMap();
        map.putAll(transactionInputInfo.toMap());
        when(cypheratorMock.cypher(map)).thenReturn("cyphered_money_by_card_number");
        when(bankMock.handleRequest(new RequestInfo("TestATMid", "cyphered_money_by_card_number",
                RequestInfo.RequestType.SEND_BY_CARD_NUM))).thenReturn(mockResponse);
        ResponseInfo<NullType> result = service.sendMoneyByCardNumber(testLoginInfo, transactionInputInfo);
        verify(bankMock).handleRequest(any(RequestInfo.class));
        assertEquals(mockResponse, result);
    }

    @Test
    void sendMoneyByPhoneNumber() {
        TransactionInputInfo transactionInputInfo = new TransactionInputInfo(null, 23, "+3333");
        ResponseInfo<NullType>mockResponse = new ResponseInfo<>(true, "okay", null);
        Map<String, Object> map = testLoginInfo.toMap();
        map.putAll(transactionInputInfo.toMap());
        when(cypheratorMock.cypher(map)).thenReturn("cyphered_money_by_phone_number");
        when(bankMock.handleRequest(new RequestInfo("TestATMid", "cyphered_money_by_phone_number",
                RequestInfo.RequestType.SEND_BY_PHONE_NUM))).thenReturn(mockResponse);
        ResponseInfo<NullType> result = service.sendMoneyByPhoneNumber(testLoginInfo, transactionInputInfo);
        verify(bankMock).handleRequest(any(RequestInfo.class));
        assertEquals(mockResponse, result);
    }

    @Test
    void withdrawMoney() {
        int amount = 2;
        Map<String, Object> map = testLoginInfo.toMap();
        map.put("amount", 2);
        ResponseInfo<NullType>mockResponse = new ResponseInfo<>(true, "okay", null);
        when(cypheratorMock.cypher(map)).thenReturn("cyphered_withdrawal");
        when(bankMock.handleRequest(new RequestInfo("TestATMid", "cyphered_withdrawal",
                RequestInfo.RequestType.WITHDRAW))).thenReturn(mockResponse);
        ResponseInfo<NullType> result = service.withdrawMoney(testLoginInfo, amount);
        verify(bankMock).handleRequest(any(RequestInfo.class));
        assertEquals(mockResponse, result);
    }

    @Test
    void authorize() {
        ResponseInfo<Boolean> mockResponse =new ResponseInfo<>(true, "okay", true);
        when(bankMock.handleRequest(new RequestInfo("TestATMid", "cyphered", RequestInfo.RequestType.AUTHORIZE))).thenReturn(mockResponse);
        ResponseInfo<Boolean> result = service.authorize(testLoginInfo);
        verify(bankMock).handleRequest(any(RequestInfo.class));
        assertEquals(mockResponse, result);
    }

    @Test
    void getBalance() {
        ResponseInfo<Long> mockResponse =new ResponseInfo<>(true, "okay", (long)23);
        when(bankMock.handleRequest(new RequestInfo("TestATMid", "cyphered", RequestInfo.RequestType.GET_BALANCE))).thenReturn(mockResponse);
        ResponseInfo<Long> result = service.getBalance(testLoginInfo);
        verify(bankMock).handleRequest(any(RequestInfo.class));
        assertEquals(mockResponse, result);
    }

    @Test
    void getCardInformation() {
        ResponseInfo<CardDTO> mockResponse =new ResponseInfo<>(true, "okay",
                new CardDTO("23423442", "223",
                        "fullname", 2023, 2, "2222", 2222, 23324, 2, 2));
        when(bankMock.handleRequest(new RequestInfo("TestATMid", "cyphered", RequestInfo.RequestType.GET_CARD_INFO))).thenReturn(mockResponse);
        ResponseInfo<CardDTO> result = service.getCardInformation(testLoginInfo);
        verify(bankMock).handleRequest(any(RequestInfo.class));
        assertEquals(mockResponse, result);
    }

    @Test
    void getAccountInformation() {
        ResponseInfo<AccountDTO> mockResponse =new ResponseInfo<>(true, "okay",
                new AccountDTO(2222, "owner_name",
                        "surname", new Date(22, 1,1), "+3333334"));
        when(bankMock.handleRequest(new RequestInfo("TestATMid", "cyphered", RequestInfo.RequestType.GET_PERSONAL_INFO))).thenReturn(mockResponse);
        ResponseInfo<AccountDTO> result = service.getAccountInformation(testLoginInfo);
        verify(bankMock).handleRequest(any(RequestInfo.class));
        assertEquals(mockResponse, result);
    }
}