package com.example.atmchetvirochka.model.general;

import java.util.HashMap;
import java.util.Map;

public class TransactionInputInfo implements Mappable {
    public final String card_number;
    public final int amount;
    public final String phone_number;
    public TransactionInputInfo(String card_number, int amount, String phone_number){
        this.card_number = card_number;
        this.amount = amount;
        this.phone_number = phone_number;
    }

    public Map<String, Object> toMap() {
        Map<String, Object>map = new HashMap<>();
        map.put("card_number", card_number);
        map.put("amount", amount);
        map.put("phone_number", phone_number);
        return map;
    }

    public static TransactionInputInfo fromMap(Map<String, Object> map) {
        return new TransactionInputInfo((String) map.get("card_number"),
                (int) map.get("amount"),
                (String) map.get("phone_number"));
    }
}