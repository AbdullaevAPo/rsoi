package ru.bmstu.rsoi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bmstu.rsoi.entity.Token;

/**
 * Created by S.burykin on 13.11.2016.
 */
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByAccessToken(String accessToken);

    Token findByRefreshToken(String refreshToken);
}
