package ru.bmstu.rsoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.rsoi.dao.BookInstanceRepository;
import ru.bmstu.rsoi.dao.BookRepository;
import ru.bmstu.rsoi.dao.LibraryVisitorRepository;
import ru.bmstu.rsoi.entity.Book;
import ru.bmstu.rsoi.entity.BookInstance;
import ru.bmstu.rsoi.entity.LibraryVisitor;
import ru.bmstu.rsoi.web.exception.ImpossibleOperationException;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by ali on 22.11.16.
 */
@Service
@Scope
@Transactional
public class BookInstanceServiceImpl implements BookInstanceService {

    @Autowired
    private BookInstanceRepository bookInstanceRepository;

    @Autowired
    private LibraryVisitorRepository visitorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookInstance> addNewBooks(int bookId, int cnt) {
        Book book = bookRepository.findOne(bookId);
        List<BookInstance> instances =
            IntStream.of(cnt).mapToObj(i -> new BookInstance(null, book)).collect(Collectors.toList());
        return bookInstanceRepository.save(instances);
    }

    @Override
    public BookInstance bindBookToVisitor(int bookId, int visitorId) {
        BookInstance bookInstance = bookInstanceRepository.findAvailableBookInstances(bookId).get(0);
        bookInstance.setVisitor(visitorRepository.findOne(visitorId));
        bookInstance = bookInstanceRepository.save(bookInstance);
        return bookInstance;
    }

    @Override
    public BookInstance unbindBookFromVisitor(int bookId, int bookInstanceId, int version) {
        BookInstance bookInstance = bookInstanceRepository.findOne(bookInstanceId);
        if (bookId != bookInstance.getBook().getId())
            throw new InvalidParameterException(String.format("Экземпляр № %d не принадлежит книге № %d " , bookId, bookInstanceId));

        bookInstance.setVisitor(null);
        return bookInstance;
    }


}
