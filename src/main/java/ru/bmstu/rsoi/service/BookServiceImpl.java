package ru.bmstu.rsoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.rsoi.dao.BookRepository;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.entity.Book;

import java.util.List;
import java.util.stream.Collectors;

import static ru.bmstu.rsoi.dto.SearchRequst.PAGE_SIZE;

/**
 * Created by ali on 26.11.16.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class BookServiceImpl implements BookService {

    @Autowired
    @Qualifier("authorService")
    private PersonService<Author> authorService;

    @Autowired
    private BookRepository repository;

    @Override
    public Book mergeBook(Integer id, String bookName, List<Integer> authorIds, Integer version) {
        List<Author> authors = authorIds.stream()
            .map(i -> authorService.get(i)).collect(Collectors.toList());
        Book book;
        if (id == null)
            book = new Book(bookName, authors);
        else {
            book = findBookById(id);
            book.setVersion(version);
        }
        return repository.save(book);
    }

    @Override
    public List<Book> findBook(String authorName, String bookName, int pageNum) {
        return repository.search(authorName, bookName, new PageRequest(pageNum, PAGE_SIZE));
    }

    @Override
    public Book findBookById(int bookId) {
        return repository.findOne(bookId);
    }
}
