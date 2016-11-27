package ru.bmstu.rsoi.dto;

import java.util.List;

/**
 * Created by ali on 26.11.16.
 */
public class BookUpdateRequest extends UpdateVersionedEntityRequest{
    private String bookName;
    private List<Integer> authors;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<Integer> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Integer> authors) {
        this.authors = authors;
    }
}
