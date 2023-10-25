package com.example.atmchetvirochka.model.dto;

import java.util.Date;

public class AccountDTO {
    public final long account_id;
    public final String name;
    public final String surname;
    public final Date birth;
    public final String phone_number;
    public AccountDTO(long account_id, String name, String surname, Date birth, String phone_number){
        this.account_id = account_id;
        this.name = name;
        this.surname = surname;
        this.birth = birth;
        this.phone_number = phone_number;
    }
}
