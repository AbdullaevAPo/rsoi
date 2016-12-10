package ru.bmstu.rsoi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.Gson;
import lombok.Data;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ali on 20.11.16.
 */
@MappedSuperclass
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class)
public @Data class Person extends VersionedEntity {
    private static final long serialVersionUID = 1L;

    protected String name;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date birthDate;

    public Person(String name, Date birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public Person() {
    }
}
