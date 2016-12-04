package ru.bmstu.rsoi.dto;

import ru.bmstu.rsoi.entity.VersionedEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 26.11.16.
 */
public class BookUpdateRequest extends UpdateVersionedEntityRequest{
    private String bookName;
    private int[] authors;

    public BookUpdateRequest(String bookName, int[] authors, int version) {
        this.bookName = bookName;
        this.authors = authors;
        this.version = version;
    }

    public BookUpdateRequest(String bookName, int[] authors) {
        this.bookName = bookName;
        this.authors = authors;
    }

    public BookUpdateRequest() {
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int[] getAuthors() {
        return authors;
    }

    public void setAuthors(int[] authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return VersionedEntity.toJson(this);
    }
}
