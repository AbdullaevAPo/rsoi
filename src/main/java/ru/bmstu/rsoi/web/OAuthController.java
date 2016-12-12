package ru.bmstu.rsoi.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rsoi.service.AuthService;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by S.burykin on 21.11.2016.
 */
@RestController
@RequestMapping("/oauth2")
public class OAuthController {

    @Autowired
    private AuthService authService;

    //http://127.0.0.1:8080/oauth/authorize?response_type=code&client_id=1&redirect_uri=https://ya.ru
    @RequestMapping(value = "/authorize",
            method = RequestMethod.GET)
    public String authorize (
        @RequestParam("client_id") String clientId,
        @RequestParam("redirect_uri") String redirectUri,
        @RequestParam("response_type") String responseType,
        HttpServletResponse response) {
        return authService.generateCode(clientId, redirectUri, responseType);
    }

    // http://127.0.0.1:8080/oauth/token?code=code&scope=read&grant_type=authorization_code&redirect_uri=https://ya.ru
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public @ResponseBody String getToken(
            @RequestParam(value = "client_id") String clientId,
            @RequestParam(value = "client_secret") String clientSecret,
            @RequestParam(value = "redirect_uri") String redirectUri,
            @RequestParam(value = "grant_type") String grantType,
            @RequestParam(value = "code", required=false) String code,
            @RequestParam(value = "refresh_token", required = false) String refreshToken) {
        return authService.generateOrRefreshToken(clientId, clientSecret, redirectUri, code, refreshToken);
    }
}
