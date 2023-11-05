package com.example.atmchetvirochka;

import com.example.atmchetvirochka.service.Bank;
import com.example.atmchetvirochka.service.BankDatabaseManager;
import com.example.atmchetvirochka.service.SimpleConnectorService;
import com.example.atmchetvirochka.service.SimpleCypherator;

public class ApplicationContext {
    private static Bank bank = new Bank(new BankDatabaseManager());
    private static SimpleConnectorService connectorService = new SimpleConnectorService(bank, "001", new SimpleCypherator());

    public static SimpleConnectorService getConnectorService() {
        return connectorService;
    }
}
