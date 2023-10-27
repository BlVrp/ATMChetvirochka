package com.example.atmchetvirochka;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("screens/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if(!(new File("src/main/resources/bankDatabase.sqlite").exists())) createEmptyDatabase();
        launch();
    }

    private static void createEmptyDatabase(){
        Connection connection = null;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:src/main/resources/bankDatabase.sqlite");
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