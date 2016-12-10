package ru.bmstu.rsoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.rsoi.dao.AuthorRepository;
import ru.bmstu.rsoi.entity.Author;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

import static ru.bmstu.rsoi.dto.SearchRequest.PAGE_SIZE;

/**
 * Created by ali on 26.11.16.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Scope
@Qualifier("authorService")
public class AuthorServiceImpl implements LibraryPersonService<Author> {
    @Autowired
    private AuthorRepository repository;

    @Autowired
    private EntityManager entityManager;

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

    /*
        @Query("select s from Author s join fetch s.books b " +
        " where (:name is null or s.name like concat('%', :name, '%')) " +
        " and (:firstDate is null or s.birthDate >= :firstDate)" +
        " and (:secondDate is null or s.birthDate <= :secondDate)" +
        " and (:bookName is null or b.name like concat('%', :bookName, '%'))" +
        " order by s.name asc, s.birthDate desc")
    List<Author> search(@Param("name") String name,
                        @Param("firstDate") Date firstDate,
                        @Param("secondDate" ) Date secondDate,
                        @Param("bookName") String bookName,
                        Pageable pageable);
     */
    @Override
    @Transactional(readOnly = true)
    public List<Author> search(String name, Date beginDate, Date endDate, String bookName, int pageNum) {
        entityManager.getEntityManagerFactory().getCache().evictAll();
        boolean firstParam = false;
        String queryStr = "select s from Author s left outer join s.books b ";
        if (name != null) {
            queryStr += (!firstParam ? " where ":" and ") + " s.name like concat('%', :name, '%') ";
            if (!firstParam) firstParam = true;
        }
        if (beginDate != null) {
            queryStr += (!firstParam ? " where ":" and ") + " s.birthDate >= :firstDate ";
            if (!firstParam) firstParam = true;
        }
        if (endDate != null) {
            queryStr += (!firstParam ? " where ":" and ") + " s.birthDate <= :secondDate ";
            if (!firstParam) firstParam = true;
        }
        if (bookName != null)
            queryStr += (!firstParam ? " where ":" and ") + " b.name like concat('%', :bookName, '%') ";
        queryStr += " order by s.name asc, s.birthDate desc";
        TypedQuery<Author> query = entityManager.createQuery(
            queryStr, Author.class)
            .setFirstResult((pageNum-1)*PAGE_SIZE).setMaxResults(PAGE_SIZE);
        if (name != null) query.setParameter("name", name);
        if (beginDate != null) query.setParameter("firstDate", beginDate, TemporalType.TIMESTAMP);
        if (endDate != null) query.setParameter("secondDate", endDate, TemporalType.TIMESTAMP);
        if (bookName != null) query.setParameter("bookName", bookName);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Author get(int id) {
        return repository.findOne(id);
    }
}
