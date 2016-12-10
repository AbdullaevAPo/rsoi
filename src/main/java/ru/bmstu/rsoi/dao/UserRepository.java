package ru.bmstu.rsoi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.rsoi.entity.User;

/**
 * Created by S.burykin on 15.11.2016.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
