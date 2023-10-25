package com.example.atmchetvirochka.model.general;

import java.util.Map;

public class RequestInfo {
    public enum RequestType{
        GET_PERSONAL_INFO,
        GET_CARD_INFO,
        GET_BALANCE,
        SEND_BY_CARD_NUM,
        WITHDRAW,
        SEND_BY_PHONE_NUM
    }
    public final String ATMid;
    public final Map<String, String> arguments;
    public final RequestType requestType;
    public RequestInfo(String ATMid, Map<String, String> arguments, RequestType requestType){
        this.ATMid = ATMid;
        this.arguments = arguments;
        this.requestType = requestType;
    }
}