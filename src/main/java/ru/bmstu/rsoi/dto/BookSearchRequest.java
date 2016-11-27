package ru.bmstu.rsoi.dto;

/**
 * Created by ali on 27.11.16.
 */
public class BookSearchRequest extends SearchRequst {
    private String authorName;
    private String bookName;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
