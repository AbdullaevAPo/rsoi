package ru.bmstu.rsoi.dto;

import lombok.Data;
import ru.bmstu.rsoi.entity.VersionedEntity;

import java.util.Date;

/**
 * Created by ali on 24.11.16.
 */
public @Data class LibraryPersonSearchRequest extends SearchRequest {
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

    @Override
    public String toString() {
        return VersionedEntity.toJson(this);
    }
}
