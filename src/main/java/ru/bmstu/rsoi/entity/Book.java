package ru.bmstu.rsoi.entity;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by ali on 20.11.16.
 */
@Entity
public class Book extends VersionedEntity{

    private String name;

    @ManyToMany
    @JoinTable
    private List<Author> author;

    @OneToMany(mappedBy = "book")
    private List<BookInstance> instances;

    public Book(String name, List<Author> author) {
        this.name = name;
        this.author = author;
    }

    public Book() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Author> getAuthor() {
        return author;
    }

    public void setAuthor(List<Author> author) {
        this.author = author;
    }

    public List<BookInstance> getInstances() {
        return instances;
    }

    public void setInstances(List<BookInstance> instances) {
        this.instances = instances;
    }
}

