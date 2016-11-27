package ru.bmstu.rsoi.foursquare;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ru.bmstu.rsoi.web.FoursquareController.clientId;

/**
 * Created by ali on 21.11.16.
 */

public class FoursquareApiTest {
    /*
    https://foursquare.com/oauth2/authenticate
    ?client_id=YOUR_CLIENT_ID
    &response_type=code
    &redirect_uri=YOUR_REGISTERED_REDIRECT_URI
     */
    @Test
    public void testGetRecommendedPaths() throws URISyntaxException, IOException {
        URI uri = new URIBuilder("https://foursquare.com/oauth2/authenticate")
            .addParameter("client_id", clientId)
            .addParameter("response_type", "code")
            .addParameter("redirect_uri", "http://localhost:8080/rsoi_labs/").build();
        System.out.println(uri.toURL().toExternalForm());
        System.out.println(Request.Get(uri).execute().returnResponse().toString());
    }
}
