package ru.bmstu.rsoi.dto;

import ru.bmstu.rsoi.entity.VersionedEntity;

import java.util.Date;

/**
 * Created by ali on 27.11.16.
 */
public class PersonUpdateRequest extends UpdateVersionedEntityRequest {
    private String name;
    private Date bornDate;

    public PersonUpdateRequest(String name, Date bornDate, int version) {
        this.name = name;
        this.bornDate = bornDate;
        this.version = version;
    }

    public PersonUpdateRequest(String name, Date bornDate) {
        this.name = name;
        this.bornDate = bornDate;
    }

    public PersonUpdateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    @Override
    public String toString() {
        return VersionedEntity.toJson(this);
    }
}
