package ru.bmstu.rsoi.service;

import ru.bmstu.rsoi.entity.Book;
import ru.bmstu.rsoi.entity.BookInstance;

import java.util.List;

/**
 * Created by ali on 22.11.16.
 */
public interface BookInstanceService {
    List<BookInstance> addNewBooks(int bookId, int cnt);
    BookInstance bindBookToVisitor(int bookId, int visitorId);
    BookInstance unbindBookFromVisitor(int bookId, int bookInstanceId, int version);
}
