package ru.bmstu.rsoi.controllertests;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.bmstu.rsoi.dto.LibraryPersonSearchRequest;
import ru.bmstu.rsoi.dto.PersonUpdateRequest;
import ru.bmstu.rsoi.entity.LibraryVisitor;
import ru.bmstu.rsoi.service.InitDataLoader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ali on 02.12.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LibraryVisitorControllerTest {

    private static Date bornDate = DateUtils.addDays(new Date(), -4);

    public static LibraryVisitor visitor1 = new LibraryVisitor("Пупкин", bornDate);
    public static LibraryVisitor visitor2 = new LibraryVisitor("Иванов", DateUtils.addDays(bornDate, 2));
    public static LibraryVisitor addLibraryVisitor(LibraryVisitor author) throws URISyntaxException, IOException {
        return TestUtil.put(new PersonUpdateRequest(author.getName(), author.getBirthDate()), "visitor/add", LibraryVisitor.class, true);
    }

    public static void removeLibraryVisitor(LibraryVisitor author) throws IOException {
        TestUtil.delete("visitor/" + author.getId() + "/remove", true);
    }

    public static LibraryVisitor updateLibraryVisitor(int id, PersonUpdateRequest updateRequest) throws IOException {
        return TestUtil.post(updateRequest, "visitor/" + id + "/update", LibraryVisitor.class, true);
    }

    public static LibraryVisitor[] search(LibraryPersonSearchRequest searchRequest) throws IOException {
        return TestUtil.post(searchRequest, "visitor/search", LibraryVisitor[].class, true);
    }

    public static LibraryVisitor findById(int id) throws URISyntaxException, IOException {
        return TestUtil.get("visitor/" + id, LibraryVisitor.class, true);
    }

    @Test
    public void _0loadDataTest() throws IOException, URISyntaxException {
        LibraryVisitor oldVisitor1 = visitor1;
        visitor1 = addLibraryVisitor(oldVisitor1);
        Assert.assertEquals(oldVisitor1, visitor1);
        LibraryVisitor oldVisitor2 = visitor2;
        visitor2 = addLibraryVisitor(oldVisitor2);
        Assert.assertEquals(oldVisitor2, visitor2);
    }

    @Test
    public void _1getByIdTest() throws IOException, URISyntaxException {
        for (LibraryVisitor visitor: Arrays.asList(visitor1, visitor2)) {
            LibraryVisitor findedVisitor = findById(visitor.getId());
            Assert.assertNotNull(findedVisitor.getBookList());
            Assert.assertEquals(findedVisitor, visitor);
        }
    }

    @Test
    public void _2updateDataTest() throws IOException, URISyntaxException, ParseException {
        Date newBornDate = InitDataLoader.toDate("1828-08-09");
        visitor2 = updateLibraryVisitor(visitor2.getId(), new PersonUpdateRequest(null, newBornDate));
        Assert.assertEquals(visitor2.getBirthDate(), newBornDate);
    }

    @Test
    public void _3searchTest() throws IOException, URISyntaxException, ParseException {
        Date newBornDate = InitDataLoader.toDate("1828-08-09");
        LibraryVisitor[] res = search(new LibraryPersonSearchRequest("Пуп", newBornDate, new Date(), null, 1));
        Assert.assertEquals(1, res.length);
    }
}
