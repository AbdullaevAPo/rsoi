package ru.bmstu.rsoi.service;

import ru.bmstu.rsoi.entity.Book;

import java.util.List;

/**
 * Created by ali on 22.11.16.
 */
public interface BookService {
    Book mergeBook(Integer id, String bookName, List<Integer> authors, Integer version);
    List<Book> findBook(String authorName, String bookName, int pageNum);
    Book findBookById(int bookId);
    void removeBook(int bookId);
}
