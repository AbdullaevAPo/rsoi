package ru.bmstu.rsoi.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by S.burykin on 22.11.2016.
 */
@Data
@Entity(name = "requests")
@Table
public class OAuthRequest {
    @Id
    @SequenceGenerator(name = "request_ids", sequenceName = "request_ids",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_ids")
    private Long id;

    @ManyToOne
    private AppClient company;

    @Column(name = "code")
    private String code;

    @Column(name = "username")
    private String userName;

    @Column(name = "redirect_uri")
    private String redirectUri;


}
