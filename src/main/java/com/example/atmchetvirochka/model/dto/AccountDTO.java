package com.example.atmchetvirochka.model.dto;

import java.util.Date;

public class AccountDTO {
    public final long account_id;
    public final String owner_name;
    public final String owner_surname;
    public final Date birth;
    public final String phone_number;
    public AccountDTO(long account_id, String owner_name, String surname, Date birth, String phone_number){
        this.account_id = account_id;
        this.owner_name = owner_name;
        this.owner_surname = surname;
        this.birth = birth;
        this.phone_number = phone_number;
    }
}
