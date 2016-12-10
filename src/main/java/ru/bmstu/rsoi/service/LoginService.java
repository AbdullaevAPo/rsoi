package ru.bmstu.rsoi.service;

import org.springframework.stereotype.Service;
import ru.bmstu.rsoi.entity.User;

/**
 * Created by S.burykin on 16.11.2016.
 */
@Service
public interface LoginService {
    void register(String login, String password);
    User login(String login, String password);
    boolean checkLogin(String login, String password);
}
