package ru.bmstu.rsoi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ali on 20.11.16.
 */
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

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> author) {
        this.authors = author;
    }

    public List<BookInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<BookInstance> instances) {
        this.instances = instances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (getName() != null ? !getName().equals(book.getName()) : book.getName() != null) return false;
        if (getAuthors() != null ? !Arrays.equals(receiveIds(getAuthors()), receiveIds(book.getAuthors())) : book.getAuthors() != null) return false;
        return getInstances() != null ? Arrays.equals(receiveIds(getInstances()), receiveIds(book.getInstances())) : book.getInstances() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getAuthors() != null ? getAuthors().hashCode() : 0);
        result = 31 * result + (getInstances() != null ? getInstances().hashCode() : 0);
        return result;
    }
}

