package com.example.atmchetvirochka.model.dao;

import com.example.atmchetvirochka.model.dto.TransactionDTO;
import com.example.atmchetvirochka.model.general.ResponseInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TransactionDAO extends DAO{
    public TransactionDAO(){}

    public ArrayList<TransactionDTO> findMany(String sqlFilter){
        //placeholder
        return null;
    }

    public ResponseInfo<TransactionDTO> findOneById(int id){
        TransactionDTO transactionDTO = null;
        String message = null;
        if(openConnection()){
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM BANK_TRANSACTION WHERE id = " + id);
                if(resultSet.next()){
                    transactionDTO = new TransactionDTO(resultSet.getLong("id"), resultSet.getString("sender"),
                    resultSet.getString("receiver"), resultSet.getDate("transaction_date").toLocalDate(),
                            resultSet.getTime("transaction_time").toLocalTime(), resultSet.getLong("amount"));
                }
                else message = "Transaction is not found";
            } catch (SQLException e) {
                message = e.getMessage();
            }
            closeConnection();
        }
        return new ResponseInfo<>(transactionDTO==null, message, transactionDTO);
    }
}
