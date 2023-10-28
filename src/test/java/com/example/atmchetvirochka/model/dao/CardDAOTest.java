package com.example.atmchetvirochka.model.dao;

import com.example.atmchetvirochka.ConnectionConstantHolder;
import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.dto.TransactionDTO;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.model.general.TransactionInputInfo;
import com.example.atmchetvirochka.service.DatabaseInitializator;
import org.junit.jupiter.api.*;

import javax.lang.model.type.NullType;
import java.sql.*;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CardDAOTest {
    private static CardDAO cardDAO;
    private static CardDTO cardDTO;
    @BeforeAll
    static void setUpAll(){
        DatabaseInitializator.initializeDatabase(ConnectionConstantHolder.testConnectionUrl);
        cardDAO = new CardDAO(ConnectionConstantHolder.testConnectionUrl);
        cardDTO = new CardDTO("234", "233",
                "fullName", 2202, 11, "2222", 222, 1, 2, 1);
    }

    @Test
    @Order(1)
    void create() throws SQLException {
            cardDAO.create(cardDTO);
            Connection connection = DriverManager.getConnection(ConnectionConstantHolder.testConnectionUrl);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CARD");
            int counter =0;
            while (resultSet.next()){
                assertEquals(resultSet.getString("card_number"), cardDTO.card_number);
                assertEquals(resultSet.getString("cvv"), cardDTO.cvv);
                assertEquals(resultSet.getString("full_name"), cardDTO.full_name);
                assertEquals(resultSet.getInt("due_to_year"), cardDTO.due_to_year);
                assertEquals(resultSet.getInt("due_to_month"), cardDTO.due_to_month);
                assertEquals(resultSet.getString("pin"), cardDTO.pin);
                assertEquals(resultSet.getLong("balance"), cardDTO.balance);
                assertEquals(resultSet.getInt("account_id"), cardDTO.account_id);
                assertEquals(resultSet.getInt("attempts"), cardDTO.attempts);
                assertEquals(resultSet.getInt("is_default"), cardDTO.is_default);
                counter++;
            }
            assertEquals(counter, 1);

    }



    @Test
    @Order(2)
    void findOneByIdWorksWithExistentId() {
        ResponseInfo<CardDTO> responseInfo= cardDAO.findOneById(cardDTO.card_number);
        assertEquals(responseInfo.data.card_number, cardDTO.card_number);
        assertEquals(responseInfo.data.cvv, cardDTO.cvv);
        assertEquals(responseInfo.data.full_name, cardDTO.full_name);
        assertEquals(responseInfo.data.due_to_year, cardDTO.due_to_year);
        assertEquals(responseInfo.data.due_to_month, cardDTO.due_to_month);
        assertEquals(responseInfo.data.pin, cardDTO.pin);
        assertEquals(responseInfo.data.balance, cardDTO.balance);
        assertEquals(responseInfo.data.account_id, cardDTO.account_id);
        assertEquals(responseInfo.data.attempts, cardDTO.attempts);
        assertEquals(responseInfo.data.is_default, cardDTO.is_default);

    }

    @Test
    @Order(3)
    void findOneByIdDoesNotWorkWithNonExistentId() {
        ResponseInfo<CardDTO> responseInfo= cardDAO.findOneById("5555");
        assertNull(responseInfo.data);
        assertFalse(responseInfo.success);
    }

    @Test
    @Order(4)
    void findOneByPhoneWorksWithExistentPhoneAndDefaultCard() throws SQLException {
        Connection connection = DriverManager.getConnection(ConnectionConstantHolder.testConnectionUrl);
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO ACCOUNT (owner_name," +
                " owner_surname, birth, phone_number) "+
                "VALUES ('name', 'surname', '2004-02-02', '+000')");
        ResponseInfo<CardDTO> responseInfo= cardDAO.findOneByPhone("+000");
        assertEquals(responseInfo.data.card_number, cardDTO.card_number);
        assertEquals(responseInfo.data.cvv, cardDTO.cvv);
        assertEquals(responseInfo.data.full_name, cardDTO.full_name);
        assertEquals(responseInfo.data.due_to_year, cardDTO.due_to_year);
        assertEquals(responseInfo.data.due_to_month, cardDTO.due_to_month);
        assertEquals(responseInfo.data.pin, cardDTO.pin);
        assertEquals(responseInfo.data.balance, cardDTO.balance);
        assertEquals(responseInfo.data.account_id, cardDTO.account_id);
        assertEquals(responseInfo.data.attempts, cardDTO.attempts);
        assertEquals(responseInfo.data.is_default, cardDTO.is_default);
    }

    @Test
    @Order(5)
    void findOneByPhoneDoesNotWorkWithNonExistentPhone() {
        ResponseInfo<CardDTO> responseInfo= cardDAO.findOneByPhone("+001");
        assertNull(responseInfo.data);
        assertFalse(responseInfo.success);
    }

    @Test
    @Order(6)
    void makeTransferDoesNotWorkForNegativeAmounts() {
        TransactionDTO negativeAmountTransaction = new TransactionDTO(cardDTO.card_number, null, -5);
        ResponseInfo<NullType>responseInfo = cardDAO.makeTransfer(negativeAmountTransaction);
        assertFalse(responseInfo.success);
        assertEquals(responseInfo.message, "Amount cannot be negative");
    }
    @Test
    @Order(7)
    void makeTransferDoesNotWorkIfCannotFindSender() {
        TransactionDTO noSenderTransaction = new TransactionDTO("0999", null, 5);
        ResponseInfo<NullType>responseInfo = cardDAO.makeTransfer(noSenderTransaction);
        assertFalse(responseInfo.success);
        assertEquals(responseInfo.message, "Sender does not exist");
    }
    @Test
    @Order(8)
    void makeTransferDoesNotWorkIfAmountExceedsBalance() {
        TransactionDTO tooMuchMoneyTransaction = new TransactionDTO(cardDTO.card_number, null, 223);
        ResponseInfo<NullType>responseInfo = cardDAO.makeTransfer(tooMuchMoneyTransaction);
        assertFalse(responseInfo.success);
        assertEquals(responseInfo.message, "Not enough money");
    }
    @Test
    @Order(9)
    void makeTransferWorksAsWithdrawalIfTheReceiverIsNotSpecified() throws SQLException {
        Connection connection = DriverManager.getConnection(ConnectionConstantHolder.testConnectionUrl);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM CARD");
        TransactionDTO tooMuchMoneyTransaction = new TransactionDTO(cardDTO.card_number, null, 220);
        ResponseInfo<NullType>responseInfo = cardDAO.makeTransfer(tooMuchMoneyTransaction);
        assertTrue(responseInfo.success);

    }
}