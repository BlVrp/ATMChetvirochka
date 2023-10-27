package com.example.atmchetvirochka.model.general;

import java.util.Objects;

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
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RequestInfo requestInfo = (RequestInfo) obj;
        return Objects.equals(ATMid, requestInfo.ATMid) && Objects.equals(arguments, requestInfo.arguments) && Objects.equals(requestType, requestInfo.requestType);
    }
}
