package com.example.atmchetvirochka;

import com.example.atmchetvirochka.service.*;

public class ApplicationContext {
    private static Bank bank = new Bank(new BankDatabaseManager());

    private static String cardNumber;
    private static String pin;
    private static ConnectorService connectorService = new SimpleConnectorService(bank, "001", new SimpleCypherator());

    public static void setCardNumber(String newCardNumber){
        cardNumber = newCardNumber;
    }
    public static void setPin(String newPin){
        pin = newPin;
    }
    public static String getCardNumber(){
        return cardNumber;
    }
    public static String getPin(){
        return pin;
    }

    public static ConnectorService getConnectorService() {
        return connectorService;
    }
}
