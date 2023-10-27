package com.example.atmchetvirochka.service;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SimpleCypherator implements Cypherator{
    @Override
    public String cypher(Map<String, Object> map) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objOut = null;
        try {
            objOut = new ObjectOutputStream(byteOut);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            objOut.writeObject(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Base64.getEncoder().encodeToString(byteOut.toByteArray());
    }
    @Override
    public Map<String, Object> decypher(String msg) {
        byte[] bytes = Base64.getDecoder().decode(msg);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
        ObjectInputStream objIn = null;
        try {
            objIn = new ObjectInputStream(byteIn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return (Map<String, Object>) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
