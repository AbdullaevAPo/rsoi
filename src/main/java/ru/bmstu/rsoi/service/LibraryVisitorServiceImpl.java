package ru.bmstu.rsoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.rsoi.dao.LibraryVisitorRepository;
import ru.bmstu.rsoi.dto.PersonSearchRequest;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.entity.LibraryVisitor;
import ru.bmstu.rsoi.web.exception.ImpossibleOperationException;

import java.util.Date;
import java.util.List;

import static ru.bmstu.rsoi.dto.SearchRequst.PAGE_SIZE;

/**
 * Created by ali on 26.11.16.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Qualifier("visitorService")
public class LibraryVisitorServiceImpl implements PersonService<LibraryVisitor> {
    @Autowired
    private LibraryVisitorRepository repository;

    @Override
    public LibraryVisitor mergePerson(Integer id, String name, Date bornDate, Integer version) {
        LibraryVisitor visitor;
        if (id == null)
            visitor = new LibraryVisitor(name, bornDate);
        else {
            visitor = get(id);
            visitor.setVersion(version);
        }
        return repository.save(visitor);
    }

    @Override
    public void removePerson(int id) {
        LibraryVisitor LibraryVisitor = repository.findOne(id);
        if (LibraryVisitor.getBookList().size() == 0)
            repository.delete(LibraryVisitor);
        else
            throw new ImpossibleOperationException("Нельзя вычеркнуть из списка студента не сдавшего все книги!");
    }

    @Override
    public List<LibraryVisitor> search(String name, Date beginDate, Date endDate, int pageNum) {
        return repository.search(name,
            beginDate, endDate,
            new PageRequest(pageNum, PAGE_SIZE));
    }

    @Override
    public LibraryVisitor get(int id) {
        return repository.findOne(id);
    }
}
