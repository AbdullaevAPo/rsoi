package ru.bmstu.rsoi.web;

import com.google.common.primitives.Ints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rsoi.dto.BookSearchRequest;
import ru.bmstu.rsoi.dto.BookUpdateRequest;
import ru.bmstu.rsoi.entity.Book;
import ru.bmstu.rsoi.entity.BookInstance;
import ru.bmstu.rsoi.service.BookInstanceService;
import ru.bmstu.rsoi.service.BookService;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ali on 23.11.16.
 */


// страница с книгами и поиском по имени автора/названию,
// возможностью уточнить инфу о книге, добавить или удалить какое-то количество книг
// зарегестрировать книгу за студентом (?)
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookInstanceService bookInstanceService;

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public @ResponseBody Book registerNewBook(@RequestBody BookUpdateRequest request) {
        return bookService.mergeBook(null, request.getBookName(), Ints.asList(request.getAuthors()), null);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public Book updateBook(@PathVariable int id, @RequestBody BookUpdateRequest request) {
        return bookService.mergeBook(id, request.getBookName(), request.getAuthors() != null ?
            Ints.asList(request.getAuthors()) : null, request.getVersion());
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public @ResponseBody Book[] findBook(@RequestBody BookSearchRequest request) {
        return bookService.findBook(request.getAuthorName(), request.getBookName(), request.getPageNum())
            .stream().toArray(Book[]::new);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    public @ResponseBody Book findBookById(@PathVariable int bookId) {
        return bookService.findBookById(bookId);
    }

    @RequestMapping(value = "/{bookId}/bind", method = RequestMethod.POST)
    public @ResponseBody BookInstance bindBookToVisitor(@PathVariable int bookId, @RequestParam int visitorId) {
        return bookInstanceService.bindBookToVisitor(bookId, visitorId);
    }

    // instances
    @RequestMapping(value = "/{bookId}/instances/{bookInstanceId}/unbind", method = RequestMethod.POST)
    public @ResponseBody BookInstance unbindBookFromVisitor(@PathVariable int bookId, @PathVariable int bookInstanceId, @RequestParam int version) {
        return bookInstanceService.unbindBookFromVisitor(bookId, bookInstanceId, version);
    }

    @RequestMapping(value = "/{bookId}/instances/add", method = RequestMethod.POST)
    public @ResponseBody List<BookInstance> addBookInstances(@PathVariable int bookId, @RequestParam int cnt) {
        return bookInstanceService.addNewBooks(bookId, cnt);
    }
}
