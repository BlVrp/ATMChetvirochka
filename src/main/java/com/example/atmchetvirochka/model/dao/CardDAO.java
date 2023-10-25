package com.example.atmchetvirochka.model.dao;

import com.example.atmchetvirochka.model.dto.CardDTO;
import com.example.atmchetvirochka.model.dto.TransactionDTO;

import java.util.ArrayList;

public class CardDAO {
    public CardDAO(){}

    public boolean create(CardDTO dto){
        //placeholder
        return false;
    }

    public ArrayList<CardDTO> findMany(String sqlFilter){
        //placeholder
        return null;
    }

    public CardDTO findOneById(String id){
        //placeholder
        return null;
    }

    public boolean updateOne(String id, CardDTO dto){
        //placeholder
        return false;
    }

    public boolean deleteOne(String id){
        //placeholder
        return false;
    }

    public boolean makeTransfer(TransactionDTO dto){
        //placeholder
        return false;
    }
}
