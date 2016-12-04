package ru.bmstu.rsoi.controllertests;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import ru.bmstu.rsoi.dto.PersonUpdateRequest;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.entity.VersionedEntity;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;

/**
 * Created by ali on 01.12.16.
 */
public class TestUtil {
    private static String baseUrl = "http://localhost:8080/rsoi_labs/";

    public static <Rq, Rs> Rs put(Rq request, String addPath, Class<Rs> rsClass) throws IOException {
        URI uri = UriBuilder.fromPath(baseUrl).build();
        HttpEntity httpEntity = new StringEntity(request.toString(), "UTF-8");
        HttpResponse response = Request.Put(uri.toString() + addPath).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8").body(httpEntity).execute().returnResponse();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), rsClass);
    }


    public static <Rs> Rs get(String addPath, Class<Rs> rsClass) throws IOException {
        URI uri = UriBuilder.fromPath(baseUrl).build();
        HttpResponse response = Request.Get(uri.toString() + addPath).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8").execute().returnResponse();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), rsClass);
    }

    public static void delete(String addPath) throws IOException {
        URI uri = UriBuilder.fromPath(baseUrl).build();
        HttpResponse response = Request.Delete(uri.toString() + addPath).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8").execute().returnResponse();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    public static <Rq, Rs> Rs post(Rq request, String addPath, Class<Rs> rsClass) throws IOException {
        URI uri = UriBuilder.fromPath(baseUrl).build();
        HttpEntity httpEntity = request != null ? new StringEntity(request.toString(), "UTF-8") : null;
        HttpResponse response = Request.Post(uri.toString() + addPath).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8").body(httpEntity).execute().returnResponse();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), rsClass);
    }
}
