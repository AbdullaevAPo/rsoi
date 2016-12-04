package ru.bmstu.rsoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.rsoi.dao.BookRepository;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.entity.Book;

import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.bmstu.rsoi.dto.SearchRequst.PAGE_SIZE;

/**
 * Created by ali on 26.11.16.
 */
@Service
@Scope
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BookServiceImpl implements BookService {

    @Autowired
    @Qualifier("authorService")
    private LibraryPersonService<Author> authorService;

    @Autowired
    private BookRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Book mergeBook(Integer id, String bookName, List<Integer> authorIds, Integer version) {
        List<Author> authors = new ArrayList<>();
        if (authorIds != null && authorIds.size() > 0)
            authors = authorIds.stream().map(i -> authorService.get(i)).collect(Collectors.toList());
        Book book;
        if (id == null) {
            book = new Book(bookName, authors);
        } else {
            book = findBookById(id);
            book.setVersion(version);
            if (bookName != null)
                book.setName(bookName);
            if (authors.size() > 1)
                book.setAuthors(authors);
        }
        return repository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findBook(String authorName, String bookName, int pageNum) {
        entityManager.getEntityManagerFactory().getCache().evictAll();
        boolean firstParam = false;
        String queryStr = "select b from Book b join fetch b.authors a ";
        if (authorName != null) {
            queryStr += (!firstParam ? " where ":" and ") + " a.name like concat('%', :authorName, '%') ";
            if (!firstParam) firstParam = true;
        }
        if (bookName != null)
            queryStr += (!firstParam ? " where ":" and ") + " b.name like concat('%', :bookName, '%') ";
        TypedQuery<Book> query = entityManager.createQuery(
            queryStr, Book.class)
            .setFirstResult((pageNum-1)*PAGE_SIZE).setMaxResults(PAGE_SIZE);
        if (authorName != null) query.setParameter("authorName", authorName);
        if (bookName != null) query.setParameter("bookName", bookName);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Book findBookById(int bookId) {
        return repository.findOne(bookId);
    }
}
