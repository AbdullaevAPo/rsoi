package ru.bmstu.rsoi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.bmstu.rsoi.dao.AppClientRepository;
import ru.bmstu.rsoi.dao.TokenRepository;
import ru.bmstu.rsoi.dao.UserRepository;
import ru.bmstu.rsoi.entity.AppClient;
import ru.bmstu.rsoi.entity.Token;

import javax.transaction.Transactional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by S.burykin on 13.11.2016.
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AppClientRepository companyRepository;

    @Autowired
    private LoginService loginService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String generateCode(String clientId, String redirectUrl, String responseType) {
        if (!responseType.equals("code")) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("error_code", "CODE_ERROR");
            errorNode.put("error_message", "Incorrect responseType");
            return errorNode.toString();
        }
        if (companyRepository.findByClientId(clientId) == null) {
            ObjectNode errorNode = objectMapper.createObjectNode();
            errorNode.put("error_code", "CODE_ERROR");
            errorNode.put("error_message", "Incorrect client_id");
            return errorNode.toString();
        }
        if (loginService.isLogin() == null)
            return "auth.html";
        String code = generateHeximalString(16);
        Token token = new Token();
        token.setCode(code);
        tokenRepository.save(token);
        return "redirect:" + redirectUrl + "?code=" + code;
    }

    @Override
    public String generateToken(String code) {
        if (code == null || code.isEmpty())
            return tokenError("Code is not present in the request");
        Token tokenEntity = tokenRepository.findByCode(code);
        if (tokenEntity == null)
            return tokenError("Incorrect code");
        tokenEntity = generateToken(tokenEntity);
        tokenRepository.save(tokenEntity);
        return buildTokenResponse(tokenEntity);
    }

    @Override
    public String refreshToken(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty())
            return tokenError("RefreshToken is not present in the request");
        Token tokenEntity = tokenRepository.findByRefreshToken(refreshToken);
        if (tokenEntity == null)
            return tokenError("Incorrect refresh_token");
        tokenEntity = generateToken(tokenEntity);
        tokenEntity = tokenRepository.save(tokenEntity);
        return buildTokenResponse(tokenEntity);
    }

    Token generateToken(Token token) {
        String accessToken = generateHeximalString(16);
        String refreshTokenNew = generateHeximalString(16);
        Long expiresIn = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30);

        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshTokenNew);
        token.setExpiresIn(expiresIn);
        return token;
    }

    private String buildTokenResponse(Token token) {
        ObjectNode response = objectMapper.createObjectNode();
        response.put("access_token", token.getAccessToken());
        response.put("token_type", "bearer");
        response.put("refresh_token", token.getRefreshToken());
        response.put("expires_in", token.getExpiresIn());
        return response.toString();
    }

    private String tokenError(String errorMsg) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("error_code", "TOKEN_ERROR");
        errorNode.put("error_message", errorMsg);
        return errorNode.toString();
    }

    @Override
    public String generateHeximalString(int hexLength) {
        Random randomService = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < hexLength)
            sb.append(Integer.toHexString(randomService.nextInt()));
        sb.setLength(hexLength);
        return sb.toString();
    }

    public String generateOrRefreshToken(String clientId, String clientSecret, String grantType, String code, String refreshToken) {
        AppClient appClient = companyRepository.findByClientId(clientId);
        if (appClient == null || !appClient.getClientSecret().equals(clientSecret))
            return tokenError("Incorrect  clientId/clientSecret");
        String res;
        switch (grantType) {
            case "authorization_code":
                res = generateToken(code);
            break;
            case "refresh_token":
                res = refreshToken(refreshToken);
            break;
            default:
                res = tokenError("Incorrect grantType");
                break;
        }
        return res;
    }
}
