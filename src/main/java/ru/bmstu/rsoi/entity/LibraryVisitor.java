package ru.bmstu.rsoi.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ali on 20.11.16.
 */
@Data
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class)
public class LibraryVisitor extends Person {
    @OneToMany(mappedBy = "visitor", fetch = FetchType.EAGER)
    @Size
    private List<BookInstance> bookList = new ArrayList<>();

    public LibraryVisitor(String name, Date birthDate) {
        super(name, birthDate);
    }

    public LibraryVisitor() {}
}
