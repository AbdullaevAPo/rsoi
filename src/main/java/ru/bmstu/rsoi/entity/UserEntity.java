package ru.bmstu.rsoi.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by S.burykin on 15.11.2016.
 */
@Entity
public @Data class UserEntity {

    @Id
    private String login;

    private String password;

    public UserEntity() { }

    public UserEntity(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
