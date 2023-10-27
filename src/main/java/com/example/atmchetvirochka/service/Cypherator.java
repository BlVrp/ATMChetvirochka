package com.example.atmchetvirochka.service;

import java.util.Map;

public interface Cypherator {
    String cypher(Map<String, Object> args);
    Map<String, Object> decypher(String msg);
}
