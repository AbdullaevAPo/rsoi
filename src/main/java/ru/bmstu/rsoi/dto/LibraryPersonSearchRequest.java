package ru.bmstu.rsoi.dto;

import ru.bmstu.rsoi.entity.VersionedEntity;

import java.util.Date;

/**
 * Created by ali on 24.11.16.
 */
public class LibraryPersonSearchRequest extends SearchRequst {
    private String name;
    private Date beginDate;
    private Date endDate;
    private String bookName;

    public LibraryPersonSearchRequest(String name, Date beginDate, Date endDate, String bookName, int pageNum) {
        this.name = name;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.pageNum = pageNum;
        this.bookName = bookName;
    }

    public LibraryPersonSearchRequest() {
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

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
