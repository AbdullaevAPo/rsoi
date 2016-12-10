package ru.bmstu.rsoi.controllertests;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.bmstu.rsoi.dto.BookSearchRequest;
import ru.bmstu.rsoi.dto.BookUpdateRequest;
import ru.bmstu.rsoi.entity.Book;
import ru.bmstu.rsoi.entity.BookInstance;
import ru.bmstu.rsoi.entity.LibraryVisitor;
import ru.bmstu.rsoi.entity.VersionedEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static ru.bmstu.rsoi.controllertests.AuthorControllerTest.pushkin;
import static ru.bmstu.rsoi.controllertests.AuthorControllerTest.tolstoi;
import static ru.bmstu.rsoi.controllertests.LibraryVisitorControllerTest.visitor1;

/**
 * Created by ali on 01.12.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookControllerTest {

    private static Date bornDate = DateUtils.addDays(new Date(), -4);

    public static Book ruslanAndLudmila;
    public static Book warAndPeace;
    public static Book addBook(Book book) throws URISyntaxException, IOException {
        return TestUtil.put(
            new BookUpdateRequest(book.getName(),
                book.getAuthors().stream().mapToInt(VersionedEntity::getId).toArray()),
        "book/add", Book.class, true);
    }

    public static BookInstance[] addBookInstances(Book book, int cnt) throws IOException {

        return TestUtil.post(null, "book/" + book.getId() + "/instances/add?cnt=" + cnt, BookInstance[].class, true);
//        instances.forEach(i -> {
//            assertEquals(i.getBook(), book);
//            assertNull(i.getVisitor());
//        });
    }

    public static BookInstance bindBookToVisitor(Book book, LibraryVisitor visitor) throws IOException {
        return TestUtil.post(null, "book/" + book.getId() + "/bind?visitorId=" + visitor.getId(), BookInstance.class, true);
    }

    public static BookInstance unbindBookFromVisitor(BookInstance instance) throws IOException {
        return TestUtil.post(null, "book/" + instance.getBook().getId() + "/instances/" + instance.getId() + "/unbind?version=" + instance.getVersion(), BookInstance.class, true);
    }

    public static void removeBook(Book book) throws IOException {
        TestUtil.delete("book/" + book.getId() + "/remove", true);
    }

    public static Book updateBook(int id, BookUpdateRequest updateRequest) throws IOException {
        return TestUtil.post(updateRequest, "book/" + id + "/update", Book.class, true);
    }

    public static Book[] search(BookSearchRequest searchRequest) throws IOException {
        return TestUtil.post(searchRequest, "book/search", Book[].class, true);
    }

    public static Book findById(int id) throws URISyntaxException, IOException {
        return TestUtil.get("book/" + id, Book.class, true);
    }

    @Test
    public void _0loadDataTest() throws IOException, URISyntaxException {
        ruslanAndLudmila = new Book("Руслан и Людмила", Arrays.asList(pushkin));
        warAndPeace = new Book("Война и мир", Arrays.asList(tolstoi));
        Book newRuslanAndLudmila = addBook(ruslanAndLudmila);
        assertNotNull(newRuslanAndLudmila.getId());
        assertEquals(newRuslanAndLudmila, ruslanAndLudmila);
        ruslanAndLudmila = newRuslanAndLudmila;
        Book newWarAndPeace = addBook(warAndPeace);
        assertNotNull(newWarAndPeace.getId());
        assertEquals(newWarAndPeace, warAndPeace);
        warAndPeace = newWarAndPeace;
    }

    @Test
    public void _1getByIdTest() throws IOException, URISyntaxException {
        for (Book book: Arrays.asList(warAndPeace, ruslanAndLudmila)) {
            Book findedBook = findById(book.getId());
            assertNotNull(findedBook.getInstances());
            assertNotNull(findedBook.getAuthors());
            assertEquals(findedBook, book);
        }
    }

    @Test
    public void _2updateDataTest() throws IOException, URISyntaxException, ParseException {
        String newName = "Война и мир Льва Толстого";
        String oldName = warAndPeace.getName();
        warAndPeace = updateBook(warAndPeace.getId(), new BookUpdateRequest(newName, null));
        assertEquals(newName, warAndPeace.getName());
        warAndPeace = updateBook(warAndPeace.getId(), new BookUpdateRequest(oldName, null));
        assertEquals(oldName, warAndPeace.getName());
    }

    @Test
    public void _3searchTest() throws IOException, URISyntaxException, ParseException {
        Book[] res = search(new BookSearchRequest("Пуш", null));
        assertEquals(res.length, 1);
        assertEquals(res[0].getId(), ruslanAndLudmila.getId());
        res = search(new BookSearchRequest(null, "мир"));
        assertEquals(res.length, 1);
        assertEquals(res[0].getId(), warAndPeace.getId());
    }

    @Test
    public void _4addBookInstancesTest() throws IOException {
        BookInstance[] instances = addBookInstances(warAndPeace, 1);
        assertEquals(instances.length, 1);
        BookInstance instance = instances[0];
        assertEquals(warAndPeace.getId(), instance.getBook().getId());
        warAndPeace = instance.getBook();
        assertEquals(instance, warAndPeace.getInstances().get(0));
    }

    @Test
    public void _5bindingAndUnbindingBookTest() throws IOException, URISyntaxException, ParseException {
        BookInstance instance = bindBookToVisitor(warAndPeace, visitor1);
        assertEquals(instance.getVisitor(), visitor1);
        instance = unbindBookFromVisitor(instance);
        assertEquals(warAndPeace, instance.getBook());
        assertNull(instance.getVisitor());
    }
}

