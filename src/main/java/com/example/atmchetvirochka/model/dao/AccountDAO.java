package com.example.atmchetvirochka.model.dao;

import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.general.ResponseInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountDAO extends DAO{
    public AccountDAO(){}

    public boolean create(AccountDTO dto){
        if(openConnection()){
            boolean success = true;
            try {
                statement.executeUpdate("INSERT INTO ACCOUNT (owner_name," +
                        " owner_surname, birth, phone_number) "+
                        "VALUES ('"+dto.owner_name+"', '"+dto.owner_surname+"', '"+dto.birth+"', '"+dto.phone_number+"')");
            } catch (SQLException e) {
                success = false;
            }
            closeConnection();
            return success;
        }
        return false;
    }

    public ArrayList<AccountDTO> findMany(String sqlFilter){
        //placeholder
        return null;
    }

    public ResponseInfo<AccountDTO> findOneById(long id){
        AccountDTO accountDTO = null;
        String message = null;
        if(openConnection()){
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT WHERE account_id = "+id);
                if(resultSet.next()){
                    accountDTO = new AccountDTO(resultSet.getLong("account_id"), resultSet.getString("owner_name"),
                            resultSet.getString("surname"), resultSet.getDate("birth"), resultSet.getString("phone_number"));
                }
                else message = "Account is not found";
            } catch (SQLException e) {
                message = e.getMessage();
            }
            closeConnection();
        }
        return new ResponseInfo<>(accountDTO==null, message, accountDTO);
    }

    public boolean updateOne(long id, AccountDTO dto){
        if(openConnection()){
            boolean success = true;
            try {
                statement.executeUpdate("UPDATE ACCOUNT SET owner_name = '" + dto.owner_name + "', owner_surname = '"
                        + dto.owner_surname + "', birth = '" + dto.birth + "', phone_number = '"+dto.phone_number+"' WHERE account_id = "+id);
            } catch (SQLException e) {
                success = false;
            }
            closeConnection();
            return success;
        }
        return false;
    }

    public boolean deleteOne(long id){
        if(openConnection()){
            boolean success = true;
            try {
                statement.executeUpdate("DELETE FROM ACCOUNT WHERE account_id = "+id);
            } catch (SQLException e) {
                success = false;
            }
            closeConnection();
            return success;
        }
        return false;
    }
}
