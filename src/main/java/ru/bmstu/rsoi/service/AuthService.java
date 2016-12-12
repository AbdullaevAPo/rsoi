package ru.bmstu.rsoi.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by S.burykin on 13.11.2016.
 */
public interface AuthService {
    String generateCode(String clientId, String redirectUrl, String responseType);
    String generateToken(String code);
    String refreshToken(String refreshToken);
    String generateOrRefreshToken(String clientId, String clientSecret, String grantType, String code, String refreshToken);
    String generateHeximalString(int size);
}
