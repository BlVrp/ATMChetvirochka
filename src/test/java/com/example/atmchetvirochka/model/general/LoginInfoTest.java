package com.example.atmchetvirochka.model.general;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LoginInfoTest {

    @Test
    void toMap() {
        LoginInfo loginInfo = new LoginInfo("12341", "0000");
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("card_number", "12341");
        expectedMap.put("pin", "0000");
        assertEquals(loginInfo.toMap(), expectedMap);
    }

    @Test
    void getCard_no() {
        LoginInfo loginInfo = new LoginInfo("132", "000");
        assertEquals(loginInfo.getCard_number(), "132");
    }

    @Test
    void getPin() {
        LoginInfo loginInfo = new LoginInfo("132", "000");
        assertEquals(loginInfo.getPin(), "000");
    }
}