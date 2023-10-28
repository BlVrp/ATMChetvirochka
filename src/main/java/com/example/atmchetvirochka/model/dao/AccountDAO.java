package com.example.atmchetvirochka.model.dao;

import com.example.atmchetvirochka.model.dto.AccountDTO;
import com.example.atmchetvirochka.model.general.ResponseInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AccountDAO extends DAO{
    public AccountDAO(String connectionUrl){
        super(connectionUrl);
    }

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

    public ResponseInfo<AccountDTO> findOneById(long id){
        AccountDTO accountDTO = null;
        String message = null;
        if(openConnection()){
            try {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ACCOUNT WHERE account_id = "+id);
                if(resultSet.next()){
                    String dateString = resultSet.getString("birth");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter);
                    Date result = Date.from(zonedDateTime.toInstant());
                    accountDTO = new AccountDTO(resultSet.getLong("account_id"), resultSet.getString("owner_name"),
                            resultSet.getString("owner_surname"),
                            result,
                            resultSet.getString("phone_number"));
                }
                else message = "Account is not found";
            } catch (SQLException e) {
                message = e.getMessage();
            }
            closeConnection();
        }

        return new ResponseInfo<>(accountDTO!=null, message, accountDTO);
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
