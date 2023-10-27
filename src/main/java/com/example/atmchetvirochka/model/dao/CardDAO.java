package com.example.atmchetvirochka.model.dao;

import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.dto.TransactionDTO;
import com.example.atmchetvirochka.model.general.ResponseInfo;

import javax.lang.model.type.NullType;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CardDAO extends DAO{
    public CardDAO(){}

    public boolean create(CardDTO dto){
        if(openConnection()){
            boolean success = true;
            try {
                statement.executeUpdate("INSERT INTO CARD (card_number," +
                        " cvv, full_name, due_to_year, due_to_month, account_id, pin, balance, attempts, is_default) "+
                        "VALUES ('"+dto.card_number+"', '"+dto.cvv+"', '"+dto.full_name+"', "+dto.due_to_year+", "
                        + dto.due_to_month + ", " + dto.account_id + ", '" + dto.pin + "', " + ", " + dto.balance +
                        ", " + dto.attempts + ", "+ dto.is_default+")");
            } catch (SQLException e) {
                success = false;
            }
            closeConnection();
            return success;
        }
        return false;
    }

    public ArrayList<CardDTO> findMany(String sqlFilter){
        //placeholder
        return null;
    }

    public ResponseInfo<CardDTO> findOneById(String id){
        CardDTO cardDTO = null;
        String message = null;
        if(openConnection()){
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM CARD WHERE card_number = '" + id + "'");
                if(resultSet.next()){
                    cardDTO = new CardDTO(resultSet.getString("card_number"), resultSet.getString("cvv"),
                            resultSet.getString("full_name"), resultSet.getInt("due_to_year"),
                            resultSet.getInt("due_to_month"), resultSet.getString("pin"),
                            resultSet.getLong("balance"), resultSet.getLong("account_id"),
                            resultSet.getInt("attempts"), resultSet.getInt("is_default"));
                }
                else message = "Card is not found";
            } catch (SQLException e) {
                message = e.getMessage();
            }
            closeConnection();
        }
        return new ResponseInfo<>(cardDTO==null, message, cardDTO);
    }

    public ResponseInfo<CardDTO> findOneByPhone(String phone){
        CardDTO cardDTO = null;
        String message = null;
        if(openConnection()){
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM CARD WHERE account_id IN (" +
                        " SELECT account_id FROM ACCOUNT WHERE phone_number = '" + phone + "') AND is_default = 1");
                if(resultSet.next()){
                    cardDTO = new CardDTO(resultSet.getString("card_number"), resultSet.getString("cvv"),
                            resultSet.getString("full_name"), resultSet.getInt("due_to_year"),
                            resultSet.getInt("due_to_month"), resultSet.getString("pin"),
                            resultSet.getLong("balance"), resultSet.getLong("account_id"),
                            resultSet.getInt("attempts"), resultSet.getInt("is_default"));
                }
                else message = "Default card is not found";
            } catch (SQLException e) {
                message = e.getMessage();
            }
            closeConnection();
        }
        return new ResponseInfo<>(cardDTO==null, message, cardDTO);
    }

    public boolean updateOne(String id, CardDTO dto){
        if(openConnection()){
            boolean success = true;
            try {
                statement.executeUpdate("UPDATE CARD SET cvv = '" + dto.cvv + "', full_name = '"
                        + dto.full_name + "', due_to_year = " + dto.due_to_year +
                        ", due_to_month = "+dto.due_to_month+", account_id = "+id+", pin = '"+dto.pin+
                        "', balance = " + dto.balance + ", attempts = "+dto.attempts + ", is_default = "
                        + dto.is_default+ " WHERE card_number = '"+ id +"'");
            } catch (SQLException e) {
                success = false;
            }
            closeConnection();
            return success;
        }
        return false;
    }

    public boolean deleteOne(String id){
        if(openConnection()){
            boolean success = true;
            try {
                statement.executeUpdate("DELETE FROM CARD WHERE card_number = '"+id+"'");
            } catch (SQLException e) {
                success = false;
            }
            closeConnection();
            return success;
        }
        return false;
    }

    public ResponseInfo<NullType> makeTransfer(TransactionDTO dto){
        String message = null;
        if(dto.from == null) return new ResponseInfo<>(false, "Sender is null", null);
        if(dto.amount<0) message = "Amount cannot be negative";
        else
        if(openConnection()){
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM CARD WHERE card_number = '"+dto.from+"'");
                if(resultSet.next()){
                    if(dto.to == null){
                        statement.executeUpdate("UPDATE CARD SET balance -= "+dto.amount +" WHERE card_number = '"+dto.from+"'");
                        statement.executeUpdate("INSERT INTO BANK_TRANSACTION (sender," +
                                " amount, transaction_date, transaction_time) "+
                                "VALUES ('"+dto.from+"', "+dto.amount+", '"+ Date.valueOf(LocalDate.now()) +
                                "', '"+ Time.valueOf(LocalTime.now()) +"')");
                    }
                    else
                    if(statement.executeQuery("SELECT * FROM CARD WHERE card_number = '"+dto.to+"'").next()){
                        if(resultSet.getLong("balance") >= dto.amount){
                            statement.executeUpdate("UPDATE CARD SET balance -= "+dto.amount +" WHERE card_number = '"+dto.from+"'");
                            statement.executeUpdate("UPDATE CARD SET balance += "+dto.amount +" WHERE card_number = '"+dto.to+"'");
                            statement.executeUpdate("INSERT INTO BANK_TRANSACTION (sender," +
                                    " receiver, amount, transaction_date, transaction_time) "+
                                            "VALUES ('"+dto.from+"', '"+dto.to+"', "+dto.amount+", '"+ Date.valueOf(LocalDate.now()) +
                                    "', '"+ Time.valueOf(LocalTime.now()) +"')");
                        }
                        else message = "Not enough money";
                    }
                    else message = "Receiver does not exist";
                }
                else message = "Sender does not exist";
            } catch (SQLException e) {
                message = e.getMessage();
            }
            closeConnection();
        }
        return new ResponseInfo<>(message==null, message, null);
    }
}