package ru.bmstu.rsoi.web;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.bmstu.rsoi.service.AuthService;
import ru.bmstu.rsoi.service.LoginService;

import java.util.Map;

/**
 * Created by S.burykin on 21.11.2016.
 */
@Controller
public class OAuthController {

    @Autowired
    private AuthService authServiceImpl;

    //http://127.0.0.1:8080/oauth/authorize?response_type=code&client_id=test-client&redirect_uri=https://ya.ru&scope=read
    @RequestMapping(value = "/oauth2/authorize",
            method = RequestMethod.GET)
    public String authorize (
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("response_type") String responseType) {
        return authServiceImpl.generateCode(clientId, redirectUri, responseType);
    }

    // http://127.0.0.1:8080/oauth/token?code=code&scope=read&grant_type=authorization_code&redirect_uri=https://ya.ru
    @RequestMapping(value = "/oauth2/token", method = RequestMethod.POST)
    public @ResponseBody String getToken(
            @RequestParam(value = "client_id") String clientId,
            @RequestParam(value = "client_secret") String clientSecret,
            @RequestParam(value = "redirect_uri") String redirectUri,
            @RequestParam(value = "grant_type") String grantType,
            @RequestParam(value = "code", required=false) String code,
            @RequestParam(value = "refresh_token", required = false) String refreshToken) {
        return authServiceImpl.generateOrRefreshToken(clientId, clientSecret, redirectUri, grantType, refreshToken);
    }
}
