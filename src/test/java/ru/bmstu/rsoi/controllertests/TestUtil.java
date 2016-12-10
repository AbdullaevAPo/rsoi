package ru.bmstu.rsoi.controllertests;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bmstu.rsoi.dto.PersonUpdateRequest;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.entity.VersionedEntity;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.UUID;

import static ru.bmstu.rsoi.web.OAuthChecker.checkOAuth;

/**
 * Created by ali on 01.12.16.
 */
public class TestUtil {
    private static String token = "93c8bb6b-ea71-4dd6-b122-da512522dbc0";
    private static String baseUrl = "http://localhost:8080/rsoi_labs/";

    public static <Rq, Rs> Rs put(Rq request, String addPath, Class<Rs> rsClass, boolean needOAuth) throws IOException {
        URI uri = UriBuilder.fromPath(baseUrl).build();
        HttpEntity httpEntity = new StringEntity(request.toString(), "UTF-8");
        Request putRq = Request.Put(uri.toString() + addPath).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8").body(httpEntity);
        if (needOAuth)
            putRq.setHeader("Authorization", "Bearer " + token);
        HttpResponse response = putRq.execute().returnResponse();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), rsClass);
    }


    public static <Rs> Rs get(String addPath, Class<Rs> rsClass, boolean needOAuth) throws IOException {
        URI uri = UriBuilder.fromPath(baseUrl).build();
        Request getRq = Request.Get(uri.toString() + addPath).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8");
        if (needOAuth)
            getRq.setHeader("Authorization", "Bearer " + token);
        HttpResponse response = getRq.execute().returnResponse();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), rsClass);
    }

    public static void delete(String addPath, boolean needOAuth) throws IOException {
        URI uri = UriBuilder.fromPath(baseUrl).build();
        Request deleteRq = Request.Delete(uri.toString() + addPath).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8");
        if (needOAuth)
            deleteRq.setHeader("Authorization", "Bearer " + token);
        HttpResponse response = deleteRq.execute().returnResponse();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    public static <Rq, Rs> Rs post(Rq request, String addPath, Class<Rs> rsClass, boolean needOAuth) throws IOException {
        URI uri = UriBuilder.fromPath(baseUrl).build();
        HttpEntity httpEntity = request != null ? new StringEntity(request.toString(), "UTF-8") : null;
        Request postRq = Request.Post(uri.toString() + addPath).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8")
            .body(httpEntity);
        if (needOAuth)
            postRq.setHeader("Authorization", "Bearer " + token);
        HttpResponse response = postRq.execute().returnResponse();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), rsClass);
    }
}
