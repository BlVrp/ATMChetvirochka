package com.example.atmchetvirochka.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseInitializator {
    public static void clearDatabase(String databaseUrl){
        try (Connection conn = DriverManager.getConnection(databaseUrl)) {
            if (conn != null) {
                List<String> tableNames = new ArrayList<>();
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%';")) {
                    while (rs.next()) {
                        tableNames.add(rs.getString("name"));
                    }
                }
                try (Statement stmt = conn.createStatement()) {
                    for (String tableName : tableNames) {
                        stmt.execute("DROP TABLE " + tableName + ";");
                        System.out.println("Dropped table: " + tableName);
                    }
                }
            }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        public static void initializeDatabase(String databaseUrl){
        clearDatabase(databaseUrl);
        Connection connection = null;
        Statement statement;
        try {
            connection = DriverManager.getConnection(databaseUrl);
            statement = connection.createStatement();
            statement.executeUpdate("""
                    CREATE TABLE ACCOUNT (
                      account_id INTEGER PRIMARY KEY AUTOINCREMENT,
                      owner_name VARCHAR(15) NOT NULL,
                      owner_surname VARCHAR(15) NOT NULL,
                      birth DATE NOT NULL,
                      phone_number VARCHAR(15) NOT NULL
                    );""");
            statement.executeUpdate("""
                    CREATE TABLE CARD (
                      card_number VARCHAR(16) PRIMARY KEY,
                      cvv VARCHAR(3) NOT NULL,
                      full_name VARCHAR(30) NOT NULL,
                      due_to_year INTEGER NOT NULL,
                      due_to_month INTEGER NOT NULL,
                      account_id INTEGER NOT NULL,
                      pin VARCHAR(4) NOT NULL,
                      balance INTEGER NOT NULL,
                      attempts INTEGER NOT NULL,
                      is_default INTEGER NOT NULL,
                      FOREIGN KEY (account_id) REFERENCES ACCOUNT(account_id)
                      ON DELETE NO ACTION
                      ON UPDATE CASCADE
                    );""");
            statement.executeUpdate("""
                    CREATE TABLE BANK_TRANSACTION (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      sender VARCHAR(16) NOT NULL,
                      receiver VARCHAR(16),
                      amount INTEGER NOT NULL,
                      transaction_date DATE NOT NULL,
                      transaction_time TIME NOT NULL,
                      FOREIGN KEY (sender) REFERENCES CARD(card_number)
                      ON DELETE NO ACTION
                      ON UPDATE CASCADE,
                      FOREIGN KEY (receiver) REFERENCES CARD(card_number)
                      ON DELETE NO ACTION
                      ON UPDATE CASCADE
                    );""");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(connection!=null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
