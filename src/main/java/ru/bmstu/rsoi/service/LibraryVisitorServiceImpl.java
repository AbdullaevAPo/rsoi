package ru.bmstu.rsoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.rsoi.dao.LibraryVisitorRepository;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.entity.LibraryVisitor;
import ru.bmstu.rsoi.web.exception.ImpossibleOperationException;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

import static ru.bmstu.rsoi.dto.SearchRequst.PAGE_SIZE;

/**
 * Created by ali on 26.11.16.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Qualifier("visitorService")
@Scope
public class LibraryVisitorServiceImpl implements LibraryPersonService<LibraryVisitor> {
    @Autowired
    private LibraryVisitorRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public LibraryVisitor mergePerson(Integer id, String name, Date bornDate, Integer version) {
        LibraryVisitor visitor;
        if (id == null)
            visitor = new LibraryVisitor(name, bornDate);
        else {
            visitor = get(id);
            visitor.setVersion(version);
            if (name != null)
                visitor.setName(name);
            if (bornDate != null)
                visitor.setBirthDate(bornDate);
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
    @Transactional(readOnly = true)
    public List<LibraryVisitor> search(String name, Date beginDate, Date endDate, String bookName, int pageNum) {
        boolean firstParam = false;
        entityManager.getEntityManagerFactory().getCache().evictAll();
        String queryStr = "select s from LibraryVisitor s left outer join s.bookList bl ";
        if (name != null) {
            queryStr += (!firstParam ? " where " : " and ") + " s.name like concat('%', :name, '%') ";
            if (!firstParam) firstParam = true;
        }
        if (beginDate != null) {
            queryStr += (!firstParam ? " where " : " and ") + " s.birthDate >= :firstDate ";
            if (!firstParam) firstParam = true;
        }
        if (endDate != null) {
            queryStr += (!firstParam ? " where " : " and ") + " s.birthDate <= :secondDate ";
            if (!firstParam) firstParam = true;
        }
        if (bookName != null)
            queryStr += (!firstParam ? " where " : " and ") + "  bl.book.name like concat('%', :bookName, '%') ";
        queryStr += " order by s.name asc, s.birthDate desc";
        TypedQuery<LibraryVisitor> query = entityManager.createQuery(
            queryStr, LibraryVisitor.class)
            .setFirstResult((pageNum - 1) * PAGE_SIZE).setMaxResults(PAGE_SIZE);
        if (name != null) query.setParameter("name", name);
        if (beginDate != null) query.setParameter("firstDate", beginDate, TemporalType.TIMESTAMP);
        if (endDate != null) query.setParameter("secondDate", endDate, TemporalType.TIMESTAMP);
        if (bookName != null) query.setParameter("bookName", bookName);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public LibraryVisitor get(int id) {
        return repository.findOne(id);
    }
}
