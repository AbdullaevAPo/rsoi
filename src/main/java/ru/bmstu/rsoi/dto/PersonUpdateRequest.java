package ru.bmstu.rsoi.dto;

import lombok.Data;
import ru.bmstu.rsoi.entity.VersionedEntity;

import java.util.Date;

/**
 * Created by ali on 27.11.16.
 */
public @Data class PersonUpdateRequest extends UpdateVersionedEntityRequest {
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

    @Override
    public String toString() {
        return VersionedEntity.toJson(this);
    }
}
