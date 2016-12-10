package ru.bmstu.rsoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bmstu.rsoi.dao.UserRepository;
import ru.bmstu.rsoi.entity.User;

/**
 * Created by S.burykin on 16.11.2016.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;


    @Override
    public void register(String login, String password) {
        User user = new User(login, password);
        userRepository.save(user);
    }

    @Override
    public User login(String login, String password) {
        User userEntity = userRepository.findByLogin(login);
        if (password.equals(userEntity.getPassword())) {
            return userEntity;
        } else {
            return null;
        }
    }

    @Override
    public boolean checkLogin(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user.getPassword().equals(password)) {
           return true;
        } else {
            return false;
        }
    }
}
