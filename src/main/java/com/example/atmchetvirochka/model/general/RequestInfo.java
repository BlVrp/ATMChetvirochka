package com.example.atmchetvirochka.model.general;

public class RequestInfo {
    public enum RequestType{
        GET_PERSONAL_INFO,
        GET_CARD_INFO,
        GET_BALANCE,
        SEND_BY_CARD_NUM,
        WITHDRAW,
        SEND_BY_PHONE_NUM,

        AUTHORIZE
    }
    public final String ATMid;
    public final String arguments;
    public final RequestType requestType;
    public RequestInfo(String ATMid, String arguments, RequestType requestType){
        this.ATMid = ATMid;
        this.arguments = arguments;
        this.requestType = requestType;
    }
}
