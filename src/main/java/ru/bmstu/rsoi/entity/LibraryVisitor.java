package ru.bmstu.rsoi.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Created by ali on 20.11.16.
 */
@Entity
public class LibraryVisitor extends Person {
    @ManyToMany(mappedBy = "visitor")
    @Size
    private List<BookInstance> bookList;

    public LibraryVisitor(String name, Date birthDate) {
        super(name, birthDate);
    }

    public LibraryVisitor() {
    }

    public List<BookInstance> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookInstance> bookList) {
        this.bookList = bookList;
    }
}
