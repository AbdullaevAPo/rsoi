package ru.bmstu.rsoi.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.jboss.logging.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ali on 21.11.16.
 */
@RestController
public class FoursquareController {
    public static final String clientId = "COGD0SD4K3YDN5EEIH505ZUHPJ4O0DAT3XFTF0GCCBEXVKUI";
    public static final String clientSecret = "B2SVPKPWPBPWH2D3JQRYMDUSMMBEBWFOBBNZH2PSE5DGEFPM";

    private static Logger logger = Logger.getLogger(FoursquareController.class);

    public String requestToken(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException, IOException {
        if (request.getParameterMap().containsKey("code")) {
            String code = request.getParameter("code");
            URI uri = new URIBuilder("https://foursquare.com/oauth2/access_token")
                .addParameter("client_id", clientId)
                .addParameter("client_secret", clientSecret)
                .addParameter("grant_type", "authorization_code")
                .addParameter("code", code)
                .addParameter("redirect_uri", "http://localhost:8080/rsoi_labs/").build();
            logger.info(uri.toURL().toExternalForm());

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonWithToken = Request.Get(uri).execute().returnContent().asString();
            return objectMapper.readTree(jsonWithToken).get("access_token").asText();
        }
        return null;
    }

    public RedirectView requestCode(String redirect) throws URISyntaxException, IOException {
        RedirectView redirectView = new RedirectView();

        URI uri = new URIBuilder("https://foursquare.com/oauth2/authenticate")
            .addParameter("client_id", clientId)
            .addParameter("response_type", "code")
            .addParameter("redirect_uri", "http://localhost:8080/rsoi_labs/" + redirect).build();
        redirectView.setUrl(uri.toURL().toExternalForm());
        return redirectView;
    }

    @RequestMapping(value = "/me",method = RequestMethod.GET)
    public RedirectView getInfoAboutMe(HttpServletRequest request, HttpServletResponse response) throws URISyntaxException, IOException {
        RedirectView redirectView = new RedirectView();
        if (!request.getParameterMap().containsKey("code")) {
            return requestCode("me");
        } else {
            URI uri = new URIBuilder("http://localhost:8080/rsoi_labs/fqrequest")
                .addParameter("code", request.getParameter("code"))
                .addParameter("url", "https://api.foursquare.com/v2/users/self").build();
            redirectView.setUrl(uri.toURL().toExternalForm());
        }
        return redirectView;
    }

    @RequestMapping(value = "/venue",method = RequestMethod.GET)
    public RedirectView getVenueInfo(@RequestParam("venueId") String venueId, HttpServletRequest request, HttpServletResponse response) throws URISyntaxException, IOException {
        RedirectView redirectView = new RedirectView();
        if (!request.getParameterMap().containsKey("code")) {
            return requestCode("venue?venueId="+venueId);
        } else {
            URI uri = new URIBuilder("http://localhost:8080/rsoi_labs/fqrequest")
                .addParameter("code", request.getParameter("code"))
                .addParameter("url", "https://api.foursquare.com/v2/venues/" + venueId).build();
            redirectView.setUrl(uri.toURL().toExternalForm());
        }
        return redirectView;
    }

    @RequestMapping(value = "/fqrequest",method = RequestMethod.GET)
    public String fqrequest(@RequestParam("code") String code, @RequestParam("url") String url,
                               HttpServletRequest request, HttpServletResponse response) throws IOException, URISyntaxException {
        String token = requestToken(request, response);
        URI uri = new URIBuilder(url)
            .addParameter("oauth_token", token)
            .addParameter("v", new SimpleDateFormat("yyyyMMdd").format(new Date())).build();
        String json = Request.Get(uri).execute().returnContent().asString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root  = objectMapper.readTree(json);
        String res = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        System.out.println(res);
        return  res;
    }
}
