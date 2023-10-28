package com.example.atmchetvirochka.model.general;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransactionInputInfoTest {
    private static TransactionInputInfo transactionInputInfo;
    private static Map<String, Object> expectedMap;
    @BeforeAll
    static void setUp(){
        transactionInputInfo = new TransactionInputInfo("12341", 325, null);
        expectedMap = new HashMap<>();
        expectedMap.put("destination_card_number", "12341");
        expectedMap.put("amount", 325);
        expectedMap.put("destination_phone_number", null);
    }

    @Test
    void toMap() {
            assertEquals(transactionInputInfo.toMap(), expectedMap);
    }

    @Test
    void fromMap() {
        assertEquals(TransactionInputInfo.fromMap(expectedMap).equals(transactionInputInfo),true);
    }
}