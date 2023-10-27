package com.example.atmchetvirochka.model.general;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransactionInputInfoTest {

    @Test
    void toMap() {

            TransactionInputInfo transactionInputInfo = new TransactionInputInfo("12341", 325, null);
            Map<String, Object> expectedMap = new HashMap<>();
            expectedMap.put("card_number", "12341");
            expectedMap.put("amount", 325);
            expectedMap.put("phone_number", null);
            assertEquals(transactionInputInfo.toMap(), expectedMap);

    }
}