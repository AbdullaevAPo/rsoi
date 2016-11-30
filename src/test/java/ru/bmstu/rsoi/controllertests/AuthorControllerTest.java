package ru.bmstu.rsoi.controllertests;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.sun.jndi.toolkit.url.Uri;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.junit.*;
import org.junit.runners.MethodSorters;
import ru.bmstu.rsoi.dto.PersonSearchRequest;
import ru.bmstu.rsoi.dto.PersonUpdateRequest;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.entity.VersionedEntity;
import ru.bmstu.rsoi.service.InitDataLoader;

import javax.persistence.Version;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ali on 28.11.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorControllerTest {

    private static Date bornDate = DateUtils.addDays(new Date(), -4);

    private static Author pushkin;
    private static Author tolstoi;
    public static Author addAuthor(Author author) throws URISyntaxException, IOException {
        URI uri = UriBuilder.fromPath("http://127.0.0.1:8080/rsoi_labs/author/add").build();
        HttpEntity httpEntity = new StringEntity(new PersonUpdateRequest(author.getName(), author.getBirthDate()).toString(), "UTF-8");
        HttpResponse response = Request.Put(uri).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8").body(httpEntity).execute().returnResponse();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), Author.class);
    }

    public static void removeAuthor(Author author) throws IOException {
        URI uri = UriBuilder.fromPath("http://127.0.0.1:8080/rsoi_labs/author")
            .path(String.valueOf(author.getId())).path("remove").build();
        HttpResponse response = Request.Delete(uri).execute().returnResponse();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_NO_CONTENT);
    }

    public static Author updateAuthor(int id, PersonUpdateRequest updateRequest) throws IOException {
        URI uri = UriBuilder.fromPath("http://127.0.0.1:8080/rsoi_labs/author/")
            .path(String.valueOf(id)).path("update").build();
        HttpEntity httpEntity = new StringEntity(updateRequest.toString(), "UTF-8");
        HttpResponse response = Request.Post(uri).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8").body(httpEntity).execute().returnResponse();
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), Author.class);
    }

    public static List<Author> search(PersonSearchRequest searchRequest) throws IOException {
        URI uri = UriBuilder.fromPath("http://127.0.0.1:8080/rsoi_labs/author/search").build();
        HttpEntity httpEntity = new StringEntity(searchRequest.toString(), "UTF-8");
        HttpResponse response = Request.Post(uri).setHeader("Accept", "application/json;charset=UTF-8")
            .setHeader("Content-type", "application/json;charset=UTF-8").body(httpEntity).execute().returnResponse();
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_MOVED_TEMPORARILY);
        StringWriter writer = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), List.class);
    }

    @Test
    public void _0loadDataTest() throws IOException, URISyntaxException {
        Author oldPushkin = new Author("Пушкин", bornDate);
        pushkin = addAuthor(oldPushkin);
        Assert.assertEquals(oldPushkin, pushkin);
        Author oldTolstoi = new Author("Толстой", DateUtils.addDays(bornDate, 2));
        tolstoi = addAuthor(oldTolstoi);
        Assert.assertEquals(oldTolstoi, tolstoi);
        Assert.assertEquals(findById(pushkin.getId()), pushkin);
        Assert.assertEquals(findById(tolstoi.getId()), tolstoi);
    }

    @Test
    public void _1updateDataTest() throws IOException, URISyntaxException, ParseException {
        Date newBornDate = InitDataLoader.toDate("1828-08-09");
        tolstoi = updateAuthor(tolstoi.getId(), new PersonUpdateRequest(tolstoi.getName(), newBornDate));
        Assert.assertEquals(tolstoi.getBirthDate(), newBornDate);
        Assert.assertEquals(findById(tolstoi.getId()), tolstoi);
    }

    @Test
    public void _2searchTest() throws IOException, URISyntaxException, ParseException {
        Date newBornDate = InitDataLoader.toDate("1828-08-09");
        List<Author> res = search(new PersonSearchRequest(null, newBornDate, new Date(), 1));
        Assert.assertEquals(res.size(), 2);
    }

    @Test
    public void _5removeDataTest() throws IOException, URISyntaxException {
        removeAuthor(pushkin);
        try { findById(pushkin.getId()); } catch (AssertionError e) { }
        removeAuthor(tolstoi);
        try { findById(tolstoi.getId()); } catch (AssertionError e) { }
    }

    public static Author findById(int id) throws URISyntaxException, IOException {
        URI uri = UriBuilder.fromPath("http://127.0.0.1:8080")
            .path("rsoi_labs").path("author").path(String.valueOf(id)).build();
        HttpResponse response = Request.Get(uri)
            .setHeader("Accept", "application/json;charset-UTF-8")
            .execute().returnResponse();
        Assert.assertEquals(HttpStatus.SC_MOVED_TEMPORARILY, response.getStatusLine().getStatusCode());

        StringWriter writer = new StringWriter();
        IOUtils.copy(((ByteArrayEntity)response.getEntity()).getContent(), writer, "UTF-8");
        return VersionedEntity.fromJson(writer.toString(), Author.class);
    }
}
