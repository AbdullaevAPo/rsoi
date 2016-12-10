package ru.bmstu.rsoi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rsoi.entity.User;
import ru.bmstu.rsoi.service.LoginService;
import ru.bmstu.rsoi.service.LoginServiceImpl;

import javax.validation.ConstraintViolationException;

/**
 * Created by S.burykin on 13.11.2016.
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String password, @RequestParam String login) {
        User user = loginService.login(login, password);
        if (user != null)
            return "OK";
        return "Incorrect login/password";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@RequestParam String login, @RequestParam String password) {
        try {
            loginService.register(login, password);
        } catch (ConstraintViolationException ex) {
            return "User with same login exists";
        }
        return "Ok";
    }
}
