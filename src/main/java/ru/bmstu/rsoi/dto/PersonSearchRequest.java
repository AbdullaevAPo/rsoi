package ru.bmstu.rsoi.dto;

import java.util.Date;

/**
 * Created by ali on 24.11.16.
 */
public class PersonSearchRequest extends SearchRequst {
    private String name;
    private Date beginDate;
    private Date endDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
