package com.example.atmchetvirochka.model.dao;

import com.example.atmchetvirochka.ConnectionConstantHolder;
import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.general.ResponseInfo;
import com.example.atmchetvirochka.service.DatabaseInitializator;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountDAOTest {
    private static  AccountDTO accountDTO;
    private static AccountDAO accountDAO;
    @BeforeAll
    static void setUpAll(){
        DatabaseInitializator.initializeDatabase(ConnectionConstantHolder.testConnectionUrl);
        accountDAO = new AccountDAO(ConnectionConstantHolder.testConnectionUrl);
        accountDTO = new AccountDTO(1, "name", "surname",
                new Date(2004, 2, 2), "+000");
    }

    @Test
    @Order(1)
    void createMethodWorks() throws SQLException {
        accountDAO.create(accountDTO);
        Connection connection = DriverManager.getConnection(ConnectionConstantHolder.testConnectionUrl);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT");
        int counter =0;
        while (resultSet.next()){
            assertEquals(resultSet.getString("owner_name"), accountDTO.owner_name);
            assertEquals(resultSet.getString("owner_surname"), accountDTO.owner_surname);
            assertEquals(resultSet.getString("birth"), accountDTO.birth.toString());
            assertEquals(resultSet.getString("phone_number"), accountDTO.phone_number);
            counter++;
        }
        assertEquals(counter, 1);
    }

    @Test
    @Order(2)
    void findOneByIdWorksWithExistentId() {
        AccountDAO accountDAO = new AccountDAO(ConnectionConstantHolder.testConnectionUrl);
        ResponseInfo<AccountDTO> responseInfo= accountDAO.findOneById(1);
        assertEquals(responseInfo.data.account_id, accountDTO.account_id);
        assertEquals(responseInfo.data.owner_name, accountDTO.owner_name);
        assertEquals(responseInfo.data.owner_surname, accountDTO.owner_surname);
        assertEquals(responseInfo.data.birth, accountDTO.birth);
        assertEquals(responseInfo.data.phone_number, accountDTO.phone_number);
    }
    @Test
    @Order(3)
    void findOneByIdDoesNotWorkWithNonExistentId() {
        AccountDAO accountDAO = new AccountDAO(ConnectionConstantHolder.testConnectionUrl);
        ResponseInfo<AccountDTO> responseInfo= accountDAO.findOneById(2);
        assertNull(responseInfo.data);
        assertFalse(responseInfo.success);
    }


}