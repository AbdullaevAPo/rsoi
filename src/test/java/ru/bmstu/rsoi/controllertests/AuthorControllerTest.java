package ru.bmstu.rsoi.controllertests;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;
import ru.bmstu.rsoi.dto.LibraryPersonSearchRequest;
import ru.bmstu.rsoi.dto.PersonUpdateRequest;
import ru.bmstu.rsoi.entity.Author;
import ru.bmstu.rsoi.service.InitDataLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ali on 28.11.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorControllerTest {

    private static Date bornDate = DateUtils.addDays(new Date(), -4);

    public static Author pushkin = new Author("Пушкин", bornDate);
    public static Author tolstoi = new Author("Толстой", DateUtils.addDays(bornDate, 2));
    public static Author addAuthor(Author author) throws URISyntaxException, IOException {
        return TestUtil.put(new PersonUpdateRequest(author.getName(), author.getBirthDate()), "author/add", Author.class, true);
    }

    public static void removeAuthor(Author author) throws IOException {
        TestUtil.delete("author/" + author.getId() + "/remove", true);
    }

    public static Author updateAuthor(int id, PersonUpdateRequest updateRequest) throws IOException {
        return TestUtil.post(updateRequest, "author/" + id + "/update", Author.class, true);
    }

    public static Author[] search(LibraryPersonSearchRequest searchRequest) throws IOException {
        return TestUtil.post(searchRequest, "author/search", Author[].class, true);
    }

    public static Author findById(int id) throws URISyntaxException, IOException {
        return TestUtil.get("author/" + id, Author.class, true);
    }

    @Test
    public void _0loadDataTest() throws IOException, URISyntaxException {
        Author oldPushkin = pushkin;
        pushkin = addAuthor(oldPushkin);
        Assert.assertEquals(oldPushkin, pushkin);
        Author oldTolstoi = tolstoi;
        tolstoi = addAuthor(oldTolstoi);
        Assert.assertEquals(oldTolstoi, tolstoi);
    }

    @Test
    public void _2updateDataTest() throws IOException, URISyntaxException, ParseException {
        Date newBornDate = InitDataLoader.toDate("1828-08-09");
        tolstoi = updateAuthor(tolstoi.getId(), new PersonUpdateRequest(null, newBornDate));
        Assert.assertEquals(tolstoi.getBirthDate(), newBornDate);
        Assert.assertEquals(findById(tolstoi.getId()), tolstoi);
    }

    @Test
    public void _3searchTest() throws IOException, URISyntaxException, ParseException {
        Date newBornDate = InitDataLoader.toDate("1828-08-09");
        Author[] res = search(new LibraryPersonSearchRequest("Пуш", newBornDate, new Date(), null, 1));
        Assert.assertEquals(1, res.length);
    }

    @Test
    public void _1getByIdTest() throws IOException, URISyntaxException {
        for (Author author: Arrays.asList(tolstoi, pushkin)) {
            Author findedAuthor = findById(author.getId());
            Assert.assertNotNull(findedAuthor.getBooks());
            Assert.assertEquals(findedAuthor, author);
        }
    }



}