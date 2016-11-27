package ru.bmstu.rsoi.dto;

import java.util.Date;

/**
 * Created by ali on 27.11.16.
 */
public class PersonUpdateRequest extends UpdateVersionedEntityRequest {
    private String name;
    private Date bornDate;

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
}
