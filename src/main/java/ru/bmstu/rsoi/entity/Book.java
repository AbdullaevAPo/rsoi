package ru.bmstu.rsoi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ali on 20.11.16.
 */
@Data
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class)
public class Book extends VersionedEntity{

    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable
    private List<Author> authors = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<BookInstance> instances = new ArrayList<>();

    public Book(String name, List<Author> authors) {
        this.name = name;
        this.authors = authors;
    }

    public Book() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (getName() != null ? !getName().equals(book.getName()) : book.getName() != null) return false;
        if (getAuthors() != null ? !Arrays.equals(receiveIds(getAuthors()), receiveIds(book.getAuthors())) : book.getAuthors() != null) return false;
        return getInstances() != null ? Arrays.equals(receiveIds(getInstances()), receiveIds(book.getInstances())) : book.getInstances() == null;
    }
}

