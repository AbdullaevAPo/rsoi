package ru.bmstu.rsoi.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by ali on 22.11.16.
 */
@Entity
public class BookInstance extends VersionedEntity{

    @ManyToOne
    private LibraryVisitor visitor;

    @ManyToOne
    @NotNull
    private Book book;

    public BookInstance(LibraryVisitor visitor, Book book) {
        this.visitor = visitor;
        this.book = book;
    }

    public BookInstance() {
    }

    public LibraryVisitor getVisitor() {
        return visitor;
    }

    public void setVisitor(LibraryVisitor visitor) {
        this.visitor = visitor;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
