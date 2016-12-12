package ru.bmstu.rsoi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rsoi.dto.LibraryPersonSearchRequest;
import ru.bmstu.rsoi.dto.PersonUpdateRequest;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.service.LibraryPersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ali on 23.11.16.
 */
// страница с авторами, поиском авторов по имени/дате рождения(диапозон)
// добавление автора или удаление
@RestController
@RequestMapping(value = "/author")
public class AuthorController {

    @Autowired
    @Qualifier("authorService")
    private LibraryPersonService<Author> authorService;
    
    @Autowired
    private OAuthChecker authChecker;

    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public @ResponseBody Author addAuthor(@RequestBody PersonUpdateRequest request,
                                          HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!authChecker.checkOAuth(httpRequest, response))
            return null;
        return authorService.mergePerson(null, request.getName(), request.getBornDate(), null);
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public void removeAuthor(@PathVariable int id,
                             HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!authChecker.checkOAuth(httpRequest, response))
            return;
        authorService.removePerson(id);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public @ResponseBody Author[] search(@RequestBody LibraryPersonSearchRequest searchInfo) {
        return authorService.search(searchInfo.getName(), searchInfo.getBeginDate(),
            searchInfo.getEndDate(), searchInfo.getBookName(), searchInfo.getPageNum())
            .stream().toArray(Author[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Author get(@PathVariable int id) {
        return authorService.get(id);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public @ResponseBody Author updateAuthor(@PathVariable int id, @RequestBody PersonUpdateRequest request,
                                             HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!authChecker.checkOAuth(httpRequest, response))
            return null;
        return authorService.mergePerson(id, request.getName(), request.getBornDate(), request.getVersion());
    }
}
