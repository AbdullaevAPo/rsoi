package ru.bmstu.rsoi.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ali on 04.12.16.
 */
public class OAuthChecker {

    public static boolean checkOAuth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null) {
            errorResponse(response);
            return false;
        }
        String token = tokenHeader.split(" ")[1];
        String bearer = tokenHeader.split(" ")[0];
        return token.length() == 36 && bearer.equals("Bearer");
    }

    private static void errorResponse(HttpServletResponse response) throws IOException {
        response.getWriter().write(
            "{ \"error\": \"unauthorized\", \"error_description\": \"Full authentication is required to access this resource\" }");
        response.getWriter().flush();
        response.getWriter().close();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
