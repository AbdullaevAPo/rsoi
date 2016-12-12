package ru.bmstu.rsoi.web;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bmstu.rsoi.dao.TokenRepository;
import ru.bmstu.rsoi.entity.Token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ali on 04.12.16.
 */
@Service
public class OAuthChecker {

    @Autowired
    private TokenRepository repository;

    public boolean checkOAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null) {
            errorResponse(response, "Full authentication is required to access this resource");
            return false;
        }
        String token = tokenHeader.split(" ")[1];
        String bearer = tokenHeader.split(" ")[0];
        Token tokenEntity = repository.findByAccessToken(token);
        if (!(token.length() == 36 && bearer.equals("Bearer") && tokenEntity != null)) {
            errorResponse(response, "Incorrect token");
            return false;
        }
        if (tokenEntity.getExpiresIn().compareTo(System.currentTimeMillis()) == -1) {
            errorResponse(response, "Session expired or invalid");
            return false;
        }
        return true;
    }

    private static void errorResponse(HttpServletResponse response, String msg) throws IOException {
        response.getWriter().write(
            "{ \"error\": \"unauthorized\", \"error_description\": \" " + msg + "\" }");
        response.getWriter().flush();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().close();
    }
}
