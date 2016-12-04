package ru.bmstu.rsoi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rsoi.dto.LibraryPersonSearchRequest;
import ru.bmstu.rsoi.dto.PersonUpdateRequest;
import ru.bmstu.rsoi.entity.LibraryVisitor;
import ru.bmstu.rsoi.service.LibraryPersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.bmstu.rsoi.web.OAuthChecker.checkOAuth;

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
    private LibraryPersonService<LibraryVisitor> visitorService;

    @RequestMapping(value = "add", method = RequestMethod.PUT)
    public @ResponseBody LibraryVisitor addVisitor(@RequestBody PersonUpdateRequest request,
                                                   HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!checkOAuth(httpRequest, response))
            return null;
        return visitorService.mergePerson(null, request.getName(), request.getBornDate(), null);
    }

    @RequestMapping(value = "/{id}/remove", method = RequestMethod.DELETE)
    public void removeVisitor(@PathVariable int id,
                              HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!checkOAuth(httpRequest, response))
            return;
        visitorService.removePerson(id);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public @ResponseBody LibraryVisitor[] search(@RequestBody LibraryPersonSearchRequest searchInfo) {
        return visitorService.search(searchInfo.getName(), searchInfo.getBeginDate(),
            searchInfo.getEndDate(), searchInfo.getBookName(), searchInfo.getPageNum())
        .stream().toArray(LibraryVisitor[]::new);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody LibraryVisitor get(@PathVariable int id) {
        return visitorService.get(id);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public LibraryVisitor updateVisitor(@PathVariable int id, @RequestBody PersonUpdateRequest request,
                                        HttpServletRequest httpRequest, HttpServletResponse response) throws IOException {
        if (!checkOAuth(httpRequest, response))
            return null;
        return visitorService.mergePerson(id, request.getName(), request.getBornDate(), request.getVersion());
    }
}
