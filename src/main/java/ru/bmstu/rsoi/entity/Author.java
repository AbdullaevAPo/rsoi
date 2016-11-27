package ru.bmstu.rsoi.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by ali on 20.11.16.
 */
@Entity
public class Author extends Person {

    @ManyToMany(mappedBy = "author")
    private List<Book> books;

    public Author(String name, Date birthDate) {
        super(name, birthDate);
    }

    public Author() {
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
