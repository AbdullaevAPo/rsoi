package ru.bmstu.rsoi.entity;


/**
 * Created by S.burykin on 13.11.2016.
 */

import lombok.Data;

import javax.persistence.*;

@Entity
public @Data class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accessToken;

    private String refreshToken;

    private Long expiresIn;

    private String code;

    public Token(String accessToken, String refreshToken, Long expiresIn, String code) {
        this.setAccessToken(accessToken);
        this.setRefreshToken(refreshToken);
        this.setExpiresIn(expiresIn);
        this.setCode(code);
    }

    public Token() {
    }
}
