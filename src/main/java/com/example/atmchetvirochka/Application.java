package com.example.atmchetvirochka;

import com.example.atmchetvirochka.model.dao.AccountDAO;
import com.example.atmchetvirochka.model.dao.CardDAO;
import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.general.LoginInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;
import com.example.atmchetvirochka.service.Bank;
import com.example.atmchetvirochka.service.BankDatabaseManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class Application extends javafx.application.Application {

    private static Stage primaryStage;

    public static Bank bank;
    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/example/atmchetvirochka/screens/defaultmenu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 540);
        stage.setTitle("ATM");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        bank = new Bank(new BankDatabaseManager());
        if(!(new File("src/main/resources/bankDatabase.sqlite").exists())){
            createEmptyDatabase();
            fillEmptyDatabase();
        }
        launch();
    }
    public static void changeScene(String resource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(resource));
        System.out.println(Application.class.getResource(resource));
        System.out.println(fxmlLoader.getResources());
        Scene scene = new Scene(fxmlLoader.load(), 720, 540);
        primaryStage.setScene(scene);
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
    private static void fillEmptyDatabase(){
        AccountDAO accountDAO = new AccountDAO(ConnectionConstantHolder.prodConnectionUrl);
        AccountDTO accountDTO = new AccountDTO(0, "Heg", "SoN", Date.valueOf(LocalDate.now()), "012313");
        AccountDTO accountDTO1 = new AccountDTO(1, "Ia", "oE", Date.valueOf(LocalDate.now()), "013242313");
        accountDAO.create(accountDTO);
        accountDAO.create(accountDTO1);
        CardDAO cardDAO = new CardDAO(ConnectionConstantHolder.prodConnectionUrl);
        CardDTO cardDTO = new CardDTO("12345678", "aaa", "Heg Son",
                2030, 6, "1111", 10000, 1, 5, 1);
        CardDTO cardDTO1 = new CardDTO("11111111", "aaa", "Ia oE",
                2030, 6, "1111", 5000, 2, 5, 1);
        cardDAO.create(cardDTO);
        cardDAO.create(cardDTO1);
    }
}