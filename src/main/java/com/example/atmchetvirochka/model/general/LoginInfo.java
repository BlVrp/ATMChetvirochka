package com.example.atmchetvirochka.model.general;

import java.util.HashMap;
import java.util.Map;

public class LoginInfo implements Mappable {
    public final String card_no;
    public final String pin;
    public LoginInfo(String card_no, String pin){
        this.card_no = card_no;
        this.pin = pin;
    }

    public Map<String,Object> toMap(){
      Map<String, Object> map = new HashMap<>();
      map.put("card_no", card_no);
      map.put("pin", pin);
      return map;
    }
}
