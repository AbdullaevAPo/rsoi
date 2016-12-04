package ru.bmstu.rsoi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.gson.Gson;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ali on 20.11.16.
 */
@MappedSuperclass
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class)
public class Person extends VersionedEntity {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getName() != null ? !getName().equals(person.getName()) : person.getName() != null) return false;
        return getBirthDate() != null ? getBirthDate().equals(person.getBirthDate()) : person.getBirthDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getBirthDate() != null ? getBirthDate().hashCode() : 0);
        return result;
    }
}
