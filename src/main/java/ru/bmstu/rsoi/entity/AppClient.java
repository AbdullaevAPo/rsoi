package ru.bmstu.rsoi.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by S.burykin on 21.11.2016.
 */

@Entity
@Table
public @Data class AppClient {
    @Id
    private String clientId;

    private String clientSecret;

    private String redirectUrl;

    private String companyName;

    public AppClient() {}

    public AppClient(String clientId, String clientSecret, String redirectUrl, String companyName) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.companyName = companyName;
    }

}
