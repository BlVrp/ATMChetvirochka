package com.example.atmchetvirochka.service;

import java.util.Map;

public class SimpleCypherator implements Cypherator{
    @Override
    public String cypher(Map<String, Object> args) {
        return args.toString();
    }
}
