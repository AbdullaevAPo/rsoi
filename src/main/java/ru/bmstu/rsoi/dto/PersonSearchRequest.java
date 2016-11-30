package ru.bmstu.rsoi.dto;

import ru.bmstu.rsoi.entity.VersionedEntity;

import java.util.Date;

/**
 * Created by ali on 24.11.16.
 */
public class PersonSearchRequest extends SearchRequst {
    private String name;
    private Date beginDate;
    private Date endDate;

    public PersonSearchRequest(String name, Date beginDate, Date endDate, int pageNum) {
        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.pageNum = pageNum;
    }

    public PersonSearchRequest() {
    }

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

    @Override
    public String toString() {
        return VersionedEntity.toJson(this);
    }
}
