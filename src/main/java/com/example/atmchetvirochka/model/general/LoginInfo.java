package com.example.atmchetvirochka.model.general;

import java.util.HashMap;
import java.util.Map;

public class LoginInfo implements Mappable {
    private final String card_number;
    private final String pin;

    String getCard_number(){
        return card_number;
    }

    String getPin(){
        return  pin;
    }
    public LoginInfo(String card_number, String pin){
        this.card_number = card_number;
        this.pin = pin;
    }

    public Map<String,Object> toMap(){
      Map<String, Object> map = new HashMap<>();
      map.put("card_number", card_number);
      map.put("pin", pin);
      return map;
    }

    public static LoginInfo fromMap(Map<String, Object> map) {
        return new LoginInfo((String) map.get("card_number"),
                (String) map.get("pin"));
    }
}
