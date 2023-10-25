package com.example.atmchetvirochka.model.general;

public class ResponseInfo<T> {
    public final boolean success;
    public final String message;
    public final T data;
    public ResponseInfo(boolean success, String message, T data){
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
