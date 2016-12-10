package ru.bmstu.rsoi.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by S.burykin on 15.11.2016.
 */
@Entity
public @Data class User {

    @Id
    private String login;

    private String password;

    public User() { }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
