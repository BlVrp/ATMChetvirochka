package com.example.atmchetvirochka.service;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SimpleCypheratorTest {

    @Test
    void cypherAndDecipher() {
        SimpleCypherator simpleCypherator = new SimpleCypherator();
        Map<String, Object> expectedMap = new HashMap<>();
        expectedMap.put("card_number", "12341");
        expectedMap.put("amount", 325);
        expectedMap.put("phone_number", null);
        assertEquals(simpleCypherator.decypher(simpleCypherator.cypher(expectedMap) ),expectedMap);
    }
}