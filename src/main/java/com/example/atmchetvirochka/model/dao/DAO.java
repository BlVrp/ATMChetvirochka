package com.example.atmchetvirochka.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
    protected Connection connection;
    protected Statement statement;

    private String connectionUrl;

    public DAO(String connectionUrl){
        this.connectionUrl = connectionUrl;
    }
    protected boolean openConnection() {
        try {
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
        }
        catch (SQLException e){
            return false;
        }
        return true;
    }
    protected boolean closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
