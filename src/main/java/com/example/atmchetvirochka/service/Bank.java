package com.example.atmchetvirochka.service;

import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.dto.TransactionDTO;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.RequestInfo;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;

import javax.lang.model.type.NullType;
import java.util.Map;
import java.util.Objects;

public class Bank {
    private final BankDatabaseManager bankDatabaseManager;
    private final Cypherator cypherator;

    public Bank(){
        bankDatabaseManager = new BankDatabaseManager();
        cypherator = new SimpleCypherator();
    }

    private boolean authorize(LoginInfo loginInfo){
        Map<String, Object> map = loginInfo.toMap();
        ResponseInfo<CardDTO> responseInfo = bankDatabaseManager.cardDAO.findOneById((String) (map.get("card_number")));
        if(responseInfo.success){
            return Objects.equals(responseInfo.data.pin, map.get("pin"));
        }
        return false;
    }

    public ResponseInfo<AccountDTO> getPersonalInformation(LoginInfo loginInfo){
        if(!authorize(loginInfo)) return new ResponseInfo<>(false, "Authorization failed", null);
        CardDTO cardDTO = bankDatabaseManager.cardDAO.findOneById((String) loginInfo.toMap().get("card_number")).data;
        return bankDatabaseManager.accountDAO.findOneById(cardDTO.account_id);
    }

    public ResponseInfo<CardDTO> getCardInformation(LoginInfo loginInfo){
        if(!authorize(loginInfo)) return new ResponseInfo<>(false, "Authorization failed", null);
        return bankDatabaseManager.cardDAO.findOneById((String) loginInfo.toMap().get("card_number"));
    }

    public ResponseInfo<Long> getBalance(LoginInfo loginInfo){
        ResponseInfo<CardDTO> cardDTOResponseInfo = getCardInformation(loginInfo);
        if(cardDTOResponseInfo.success){
            return new ResponseInfo<>(true, null, cardDTOResponseInfo.data.balance);
        }
        else return new ResponseInfo<>(false, cardDTOResponseInfo.message, null);
    }

    public ResponseInfo<NullType> sendMoneyByCardNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo){
        if(!authorize(loginInfo)) return new ResponseInfo<>(false, "Authorization failed", null);
        TransactionDTO transactionDTO = new TransactionDTO((String) loginInfo.toMap().get("card_number"),
                transactionInputInfo.card_number, transactionInputInfo.amount);
        return bankDatabaseManager.cardDAO.makeTransfer(transactionDTO);
    }

    public ResponseInfo<NullType> withdrawMoney(LoginInfo loginInfo, long moneyAmount){
        if(!authorize(loginInfo)) return new ResponseInfo<>(false, "Authorization failed", null);
        TransactionDTO transactionDTO = new TransactionDTO((String) loginInfo.toMap().get("card_number"),
                null, moneyAmount);
        return bankDatabaseManager.cardDAO.makeTransfer(transactionDTO);
    }

    public ResponseInfo<NullType> sendMoneyByPhoneNumber(LoginInfo loginInfo, TransactionInputInfo transactionInputInfo){
        if(!authorize(loginInfo)) return new ResponseInfo<>(false, "Authorization failed", null);
        ResponseInfo<CardDTO> cardDTOResponseInfo = bankDatabaseManager.cardDAO.findOneByPhone(transactionInputInfo.phone_number);
        if(cardDTOResponseInfo.success){
            TransactionDTO transactionDTO = new TransactionDTO((String) loginInfo.toMap().get("card_number"),
                    cardDTOResponseInfo.data.card_number, transactionInputInfo.amount);
            return bankDatabaseManager.cardDAO.makeTransfer(transactionDTO);
        }
        else return new ResponseInfo<>(false, "Default card does not exist", null);
    }

    public ResponseInfo handleRequest(RequestInfo requestInfo){
        Map<String, Object> map = cypherator.decypher(requestInfo.arguments);
        switch (requestInfo.requestType){
            case GET_PERSONAL_INFO: return getPersonalInformation(LoginInfo.fromMap(map));
            case GET_BALANCE: return getBalance(LoginInfo.fromMap(map));
            case SEND_BY_CARD_NUM: return sendMoneyByCardNumber(LoginInfo.fromMap(map), TransactionInputInfo.fromMap(map));
            case WITHDRAW: return withdrawMoney(LoginInfo.fromMap(map), (long)map.get("amount"));
            case SEND_BY_PHONE_NUM: return sendMoneyByPhoneNumber(LoginInfo.fromMap(map), TransactionInputInfo.fromMap(map));
            case AUTHORIZE: boolean aut = authorize(LoginInfo.fromMap(map));
            if(aut) return new ResponseInfo<>(true, null, true);
            else return new ResponseInfo<>(false, "Authorization failed", false);
            default: break;
        }
        return new ResponseInfo<NullType>(false, "Wrong request type.", null);
    }
}