package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dao.AccountDAO;
import com.example.atmchetvirochka.model.dao.CardDAO;
import com.example.atmchetvirochka.model.dao.TransactionDAO;
import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.dto.TransactionDTO;
import com.example.atmchetvirochka.model.general.RequestInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.lang.model.type.NullType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BankTest {
    private static BankDatabaseManager mockBankDatabaseManager;

    private static CardDAO mockCardDAO;

    private static AccountDAO mockAccountDAO;

    private static TransactionDAO mockTransactionDAO;

    private  static Map<String, Object> goodCardMap;

    private  static Map<String, Object> badCardMap;

    private static Map<String, Object> nonExistentCardMap;

    private static String goodCardId;
    private  static String goodCardPin;

    private  static String badCardId;
    private  static String badCardPin;

    private static long accountId;

    private static ResponseInfo<CardDTO> goodCardResponseInfo;


    private static CardDTO goodCardDTO;
    private static CardDTO badCardDTO;



    @BeforeAll
    static void setUpAll(){
        goodCardId = "234";
        goodCardPin = "0000";
        badCardId = "235";
        badCardPin = goodCardPin;
        goodCardMap = new HashMap<>();
        goodCardMap.put("card_number", goodCardId);
        goodCardMap.put("pin", goodCardPin);
        badCardMap = new HashMap<>();
        badCardMap.put("card_number", badCardId);
        badCardMap.put("pin", badCardPin);

        nonExistentCardMap = new HashMap<>();

        nonExistentCardMap.put("card_number", "000");
        nonExistentCardMap.put("pin", "0000");
        accountId = 2;
        goodCardDTO = new CardDTO("234", "233",
                "fullName", 2202, 11, goodCardPin, 222, accountId, 2, 0);
        badCardDTO =new CardDTO("235", "233",
                "fullName", 2202, 11, "1111", 222, accountId, 2, 1);
        goodCardResponseInfo = new ResponseInfo<>(true, "ok",goodCardDTO);
        mockBankDatabaseManager = mock(BankDatabaseManager.class);
        mockCardDAO = mock(CardDAO.class);
        mockAccountDAO = mock(AccountDAO.class);
        mockTransactionDAO = mock(TransactionDAO.class);
    }

    @BeforeEach
    void setUp(){
        when(mockBankDatabaseManager.getCardDAO()).thenReturn(mockCardDAO);
        when(mockBankDatabaseManager.getAccountDAO()).thenReturn(mockAccountDAO);
        when(mockBankDatabaseManager.getTransactionDAO()).thenReturn(mockTransactionDAO);
        when(mockCardDAO.findOneById(goodCardId)).thenReturn(goodCardResponseInfo);
        when(mockCardDAO.findOneById(badCardId)).thenReturn(new ResponseInfo<>(true, "ok", badCardDTO));
        when(mockCardDAO.findOneById(nonExistentCardMap.get("card_number").toString())).thenReturn(new ResponseInfo<>(false,
                "Card not found", null));
        when(mockCardDAO.makeTransfer(any())).thenAnswer(new Answer<ResponseInfo<NullType>>() {
            @Override
            public ResponseInfo<NullType> answer(InvocationOnMock invocation) throws Throwable {
                TransactionDTO inputTransactionDTO = (TransactionDTO)invocation.getArgument(0);
                if(inputTransactionDTO==null){
                    return null;
                }
                System.out.println(inputTransactionDTO.from + inputTransactionDTO.amount + inputTransactionDTO.to);
                if (inputTransactionDTO.from.equals(goodCardId) &&inputTransactionDTO.amount==200){
                    if(inputTransactionDTO.to==null){
                        return new ResponseInfo<NullType>(true, "out", null);
                    }
                    if (inputTransactionDTO.to.equals(badCardId)) {
                        return new ResponseInfo<NullType>(true, "to bad card", null);
                    }
                }
                return new ResponseInfo<NullType>(false, null, null);
            }});
    }

    @Test
    void authorizeReturnsTrueWhenIsExpectedTo(){
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<Boolean> responseInfo= bank.handleRequest(new RequestInfo("22", simpleCypherator.cypher(goodCardMap),
                RequestInfo.RequestType.AUTHORIZE));
        ResponseInfo<Boolean> expectedResponse = new ResponseInfo<>(true, null, true);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void authorizeReturnsFalseWhenPinIsIncorrect(){
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<Boolean> responseInfo= bank.handleRequest(new RequestInfo("22", simpleCypherator.cypher(badCardMap),
                RequestInfo.RequestType.AUTHORIZE));
        ResponseInfo<Boolean> expectedResponse = new ResponseInfo<>(false, "Authorization failed", false);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void authorizeReturnsFalseWhenThereIsNoSuchCardInTheDatabase(){
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);

        ResponseInfo<Boolean> responseInfo= bank.handleRequest(new RequestInfo("22", simpleCypherator.cypher(nonExistentCardMap),
                RequestInfo.RequestType.AUTHORIZE));
        ResponseInfo<Boolean> expectedResponse = new ResponseInfo<>(false, "Authorization failed", false);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void getPersonalInformationReturnsNullWhenAuthorizeReturnsFalse() {
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<Boolean> responseInfo= bank.handleRequest(new RequestInfo("22", simpleCypherator.cypher(badCardMap),
                RequestInfo.RequestType.GET_PERSONAL_INFO));
        ResponseInfo<Boolean> expectedResponse = new ResponseInfo<>(false, "Authorization failed", null);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void getPersonalInformationReturnsInfoWhenAuthorizeReturnsTrue(){
        AccountDTO accountDTO = new AccountDTO(accountId, "onton", "pysmennyi",
                new Date(2004, 9,2), "+0000000");
        ResponseInfo<AccountDTO> accountDTOResponseInfo = new ResponseInfo<>(true, null, accountDTO);
        when(mockAccountDAO.findOneById(accountId)).thenReturn(accountDTOResponseInfo);
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<AccountDTO> responseInfo= bank.handleRequest(new RequestInfo("22", simpleCypherator.cypher(goodCardMap),
                RequestInfo.RequestType.GET_PERSONAL_INFO));
        assertEquals(responseInfo, accountDTOResponseInfo);
    }

    @Test
    void getCardInformationReturnsNullWhenAutorizeReturnsFalse() {
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<Boolean> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(badCardMap), RequestInfo.RequestType.GET_CARD_INFO));
        ResponseInfo<Boolean> expectedResponse = new ResponseInfo<>(false, "Authorization failed", null);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void getCardInformationReturnsCardInformationWhenAuthorizeReturnsTrue(){
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<CardDTO> responseInfo= bank.handleRequest(new RequestInfo("22", simpleCypherator.cypher(goodCardMap),
                RequestInfo.RequestType.GET_CARD_INFO));
        assertEquals(responseInfo, goodCardResponseInfo);
    }

    @Test
    void getBalanceReturnsNullIfAuthorizeReturnsFalse() {
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<Boolean> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(badCardMap), RequestInfo.RequestType.GET_BALANCE));
        ResponseInfo<Boolean> expectedResponse = new ResponseInfo<>(false, "Authorization failed", null);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void getBalanceReturnsBalanceIfAuthorizeReturnsTrue() {
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<Long> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(goodCardMap), RequestInfo.RequestType.GET_BALANCE));
        ResponseInfo<Long>expectedResponse = new ResponseInfo<>(true, "ok", goodCardDTO.balance);
        assertEquals(responseInfo, expectedResponse);
    }


    @Test
    void sendMoneyByCardNumberReturnsNullIfAuthorizeReturnsFalse() {
        Map<String, Object> requestMap = new HashMap<>(badCardMap);
        requestMap.put("amount", 200);
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<Boolean> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(requestMap), RequestInfo.RequestType.SEND_BY_CARD_NUM));
        ResponseInfo<Boolean> expectedResponse = new ResponseInfo<>(false, "Authorization failed", null);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void sendMoneyByCardNumberReturnsNullWithSuccessIfAuthorizeReturnsTrue() {
        Map<String, Object> requestMap = new HashMap<>(goodCardMap);
        requestMap.put("amount", 200);
        requestMap.put("destination_card_number", badCardId);
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<NullType> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(requestMap), RequestInfo.RequestType.SEND_BY_CARD_NUM));
        ResponseInfo<NullType> expectedResponse = new ResponseInfo<>(true, "to bad card", null);
        assertEquals(responseInfo, expectedResponse);
    }



    @Test
    void withdrawMoneyReturnsNullIfAuthorizeReturnsFalse() {
        Map<String, Object> requestMap = new HashMap<>(badCardMap);
        requestMap.put("amount", 200);
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<Boolean> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(requestMap), RequestInfo.RequestType.WITHDRAW));
        ResponseInfo<Boolean> expectedResponse = new ResponseInfo<>(false, "Authorization failed", null);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void withdrawMoneyReturnsNullWithSuccessIfAuthorizeReturnsTrue() {
        Map<String, Object> requestMap = new HashMap<>(goodCardMap);
        requestMap.put("amount", 200);
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<Boolean> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(requestMap), RequestInfo.RequestType.WITHDRAW));
        ResponseInfo<NullType> expectedResponse = new ResponseInfo<>(true, "out", null);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void sendMoneyByPhoneNumberReturnsNullIfAuthorizeReturnsFalse() {
        Map<String, Object> requestMap = new HashMap<>(badCardMap);
        requestMap.put("amount", 200);
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<NullType> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(requestMap), RequestInfo.RequestType.SEND_BY_PHONE_NUM));
        ResponseInfo<Boolean> expectedResponse = new ResponseInfo<>(false, "Authorization failed", null);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void sendMoneyByPhoneNumberReturnsNullWithSuccessIfAuthorizeReturnsTrue() {
        String phone = "+3333333";
        when(mockCardDAO.findOneByPhone(phone)).thenReturn(new ResponseInfo<>(true, "ok", badCardDTO));
        Map<String, Object> requestMap = new HashMap<>(goodCardMap);
        requestMap.put("amount", 200);
        requestMap.put("destination_phone_number", "+3333333");
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<NullType> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(requestMap), RequestInfo.RequestType.SEND_BY_PHONE_NUM));
        ResponseInfo<NullType> expectedResponse = new ResponseInfo<>(true, "to bad card", null);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void sendMoneyByPhoneNumberReturnsNullIfNoDefaultCardSelected(){
        String phone = "+3333333";
        when(mockCardDAO.findOneByPhone(phone)).thenReturn(new ResponseInfo<>(false, " not ok", null));
        Map<String, Object> requestMap = new HashMap<>(goodCardMap);
        requestMap.put("amount", 200);
        requestMap.put("destination_phone_number", "+3333333");
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<NullType> responseInfo= bank.handleRequest(new RequestInfo("22",
                simpleCypherator.cypher(requestMap), RequestInfo.RequestType.SEND_BY_PHONE_NUM));
        ResponseInfo<NullType> expectedResponse = new ResponseInfo<>(false, "Default card does not exist", null);
        assertEquals(responseInfo, expectedResponse);
    }

    @Test
    void returnsNullIfThereIsNoSuchRequestTypeForUser(){
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Bank bank = new Bank(mockBankDatabaseManager);
        ResponseInfo<NullType>responseInfo = bank.handleRequest(new RequestInfo("22", simpleCypherator.cypher(goodCardMap),
                RequestInfo.RequestType.MAINTENANCE));
        assertEquals(responseInfo, new ResponseInfo<NullType>(false, "Wrong request type.", null));
    }

}