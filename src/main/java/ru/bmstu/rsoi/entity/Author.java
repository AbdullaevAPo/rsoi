package ru.bmstu.rsoi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by ali on 20.11.16.
 */
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class)
public class Author extends Person {

    @ManyToMany(mappedBy = "author", fetch = FetchType.EAGER)
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
