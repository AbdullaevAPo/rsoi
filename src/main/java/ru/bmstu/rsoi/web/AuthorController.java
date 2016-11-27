package ru.bmstu.rsoi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rsoi.dto.PersonSearchRequest;
import ru.bmstu.rsoi.dto.PersonUpdateRequest;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.service.PersonService;

import java.util.Date;
import java.util.List;

/**
 * Created by ali on 23.11.16.
 */
// страница с авторами, поиском авторов по имени/дате рождения(диапозон)
// добавление автора или удаление
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    @Qualifier("authorService")
    private PersonService<Author> authorService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/add", method = RequestMethod.PUT)
    public @ResponseBody Author addAuthor(@RequestBody PersonUpdateRequest request) {
        return authorService.mergePerson(null, request.getName(), request.getBornDate(), null);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public void removeAuthor(@PathVariable int id) {
        authorService.removePerson(id);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<Author> search(@RequestBody PersonSearchRequest searchInfo) {
        return authorService.search(searchInfo.getName(), searchInfo.getBeginDate(),
            searchInfo.getEndDate(), searchInfo.getPageNum());
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Author get(@PathVariable int id) {
        return authorService.get(id);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public Author updateAuthor(@PathVariable int id, @RequestBody PersonUpdateRequest request) {
        return authorService.mergePerson(id, request.getName(), request.getBornDate(), request.getVersion());
    }
}
