package ru.bmstu.rsoi.service;

import ru.bmstu.rsoi.entity.UserEntity;

/**
 * Created by S.burykin on 16.11.2016.
 */
public interface LoginService {
    void register(String login, String password);
    UserEntity login(String login, String password);
    UserEntity isLogin();
    String logout();
}
