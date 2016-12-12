package ru.bmstu.rsoi.web;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rsoi.entity.UserEntity;
import ru.bmstu.rsoi.service.LoginService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

/**
 * Created by S.burykin on 13.11.2016.
 */
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String password, @RequestParam String login, HttpServletResponse response) {
        UserEntity user = loginService.login(login, password);
        if (user != null)
            return "Ok";
        response.setStatus(HttpStatus.SC_BAD_REQUEST);
        return "Incorrect login/password";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestParam String login, @RequestParam String password, HttpServletResponse response) {
        try {
            loginService.register(login, password);
        } catch (ConstraintViolationException ex) {
            response.setStatus(HttpStatus.SC_BAD_REQUEST);
            return "UserEntity with same login exists";
        }
        return "Ok";
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public String getCurrentUser(HttpServletResponse response) throws IOException {
        if (loginService.isLogin() != null)
            return loginService.isLogin().getLogin();
        response.setStatus(HttpStatus.SC_NOT_FOUND);
        response.flushBuffer();
        return "No";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout() {
        return loginService.logout();
    }
}
