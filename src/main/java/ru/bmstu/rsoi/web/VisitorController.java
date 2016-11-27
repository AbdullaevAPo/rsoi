package ru.bmstu.rsoi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rsoi.dto.PersonSearchRequest;
import ru.bmstu.rsoi.dto.PersonUpdateRequest;
import ru.bmstu.rsoi.entity.LibraryVisitor;
import ru.bmstu.rsoi.service.PersonService;

import java.util.Date;
import java.util.List;

/**
 * Created by ali on 23.11.16.
 */
// страница со студентами с поиком студента по имени/кафедре
// и шардингом инфы про студента (в частности книги его)
// + возможность добавить нового студента или удалить выделенного студента
// + возможность открепить книгу у студента
@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    @Qualifier("visitorService")
    private PersonService<LibraryVisitor> visitorService;

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "add", method = RequestMethod.PUT)
    public @ResponseBody LibraryVisitor addVisitor(@RequestBody PersonUpdateRequest request) {
        return visitorService.mergePerson(null, request.getName(), request.getBornDate(), null);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public void removeVisitor(@PathVariable int id) {
        visitorService.removePerson(id);
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public List<LibraryVisitor> search(@RequestBody PersonSearchRequest searchInfo) {
        return visitorService.search(searchInfo.getName(), searchInfo.getBeginDate(),
            searchInfo.getEndDate(), searchInfo.getPageNum());
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody LibraryVisitor get(@PathVariable int id) {
        return visitorService.get(id);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public LibraryVisitor updateVisitor(@PathVariable int id, @RequestBody PersonUpdateRequest request) {
        return visitorService.mergePerson(id, request.getName(), request.getBornDate(), request.getVersion());
    }
}
