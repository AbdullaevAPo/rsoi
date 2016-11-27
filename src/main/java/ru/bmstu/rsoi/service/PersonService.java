package ru.bmstu.rsoi.service;

import ru.bmstu.rsoi.dto.PersonSearchRequest;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.entity.Person;

import java.util.Date;
import java.util.List;

/**
 * Created by ali on 22.11.16.
 */
public interface PersonService<T extends Person> {
    T mergePerson(Integer id, String name, Date bornDate, Integer version);
    void removePerson(int id);
    List<T> search(String name, Date beginDate, Date endDate, int pageNum);
    T get(int id);
}
