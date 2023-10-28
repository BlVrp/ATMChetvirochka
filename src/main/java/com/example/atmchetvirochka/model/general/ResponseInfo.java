package com.example.atmchetvirochka.model.general;

import java.util.Objects;

public class ResponseInfo<T> {
    public final boolean success;
    public final String message;
    public final T data;
    public ResponseInfo(boolean success, String message, T data){
        this.success = success;
        this.message = message;
        this.data = data;
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ResponseInfo requestInfo = (ResponseInfo) obj;
        return Objects.equals(success, requestInfo.success) && Objects.equals(message, requestInfo.message) && Objects.equals(data, requestInfo.data);
    }

    @Override
    public String toString(){
        return "("+this.success +" "+this.message+" "+this.data+")";
    }
}
