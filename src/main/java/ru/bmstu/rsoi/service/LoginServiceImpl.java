package ru.bmstu.rsoi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.bmstu.rsoi.dao.UserRepository;
import ru.bmstu.rsoi.entity.UserEntity;

import javax.transaction.Transactional;

/**
 * Created by S.burykin on 16.11.2016.
 */
@Service
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    private UserEntity lastLogonUser = null;

    @Override
    public void register(String login, String password) {
        UserEntity user = new UserEntity(login, password);
        lastLogonUser = userRepository.save(user);
    }

    @Override
    public UserEntity login(String login, String password) {
        UserEntity userEntity = userRepository.findByLogin(login);
        if (userEntity == null)
            return null;
        if (password.equals(userEntity.getPassword())) {
            lastLogonUser = userEntity;
            return userEntity;
        } else {
            return null;
        }
    }

    @Override
    public UserEntity isLogin() {
        return lastLogonUser;
    }

    @Override
    public String logout() {
        if (lastLogonUser != null) {
            lastLogonUser = null;
            return "Ok";
        }
        return "First login, please";
    }
}
