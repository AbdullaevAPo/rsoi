package ru.bmstu.rsoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.rsoi.dao.AuthorRepository;
import ru.bmstu.rsoi.dto.PersonSearchRequest;
import ru.bmstu.rsoi.entity.Author;

import java.util.Date;
import java.util.List;

import static ru.bmstu.rsoi.dto.SearchRequst.PAGE_SIZE;

/**
 * Created by ali on 26.11.16.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Qualifier("authorService")
public class AuthorServiceImpl implements PersonService<Author> {
    @Autowired
    private AuthorRepository repository;

    @Override
    public Author mergePerson(Integer id, String name, Date bornDate, Integer version) {
        Author author;
        if (id == null)
            author = new Author(name, bornDate);
        else {
            author = get(id);
            author.setVersion(version);
            author.setName(name);
            author.setBirthDate(bornDate);
        }
        return repository.save(author);
    }

    @Override
    public void removePerson(int id) {
        repository.delete(id);
    }

    @Override
    public List<Author> search(String name, Date beginDate, Date endDate, int pageNum) {
        return repository.search(name, beginDate, endDate, new PageRequest(pageNum, PAGE_SIZE));
    }

    @Override
    public Author get(int id) {
        return repository.findOne(id);
    }
}
