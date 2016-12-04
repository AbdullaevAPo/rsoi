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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ru.bmstu.rsoi.web.OAuthChecker.checkOAuth;

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
    public @ResponseBody Book registerNewBook(@RequestBody BookUpdateRequest bookUpdateRequest,
                                              HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!checkOAuth(httpRequest, response))
            return null;
        return bookService.mergeBook(null, bookUpdateRequest.getBookName(), Ints.asList(bookUpdateRequest.getAuthors()), null);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public Book updateBook(@PathVariable int id, @RequestBody BookUpdateRequest bookUpdaterequest,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!checkOAuth(request, response))
            return null;
        return bookService.mergeBook(id, bookUpdaterequest.getBookName(), bookUpdaterequest.getAuthors() != null ?
            Ints.asList(bookUpdaterequest.getAuthors()) : null, bookUpdaterequest.getVersion());
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
    public @ResponseBody BookInstance bindBookToVisitor(@PathVariable int bookId, @RequestParam int visitorId,
                                                        HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!checkOAuth(httpRequest, response))
            return null;
        return bookInstanceService.bindBookToVisitor(bookId, visitorId);
    }

    // instances
    @RequestMapping(value = "/{bookId}/instances/{bookInstanceId}/unbind", method = RequestMethod.POST)
    public @ResponseBody BookInstance unbindBookFromVisitor(@PathVariable int bookId,
                                                            @PathVariable int bookInstanceId, @RequestParam int version,
                                                            HttpServletRequest httpRequest,
                                                            HttpServletResponse response) throws IOException {
        if (!checkOAuth(httpRequest, response))
            return null;
        return bookInstanceService.unbindBookFromVisitor(bookId, bookInstanceId, version);
    }

    @RequestMapping(value = "/{bookId}/instances/add", method = RequestMethod.POST)
    public @ResponseBody List<BookInstance> addBookInstances(@PathVariable int bookId, @RequestParam int cnt,
                                                             HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!checkOAuth(httpRequest, response))
            return null;
        return bookInstanceService.addNewBooks(bookId, cnt);
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public void removeBook(@PathVariable int id,
                             HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!checkOAuth(httpRequest, response))
            return;
        bookService.removeBook(id);
    }
}
