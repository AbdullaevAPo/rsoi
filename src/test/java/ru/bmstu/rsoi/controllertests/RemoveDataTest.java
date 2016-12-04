package ru.bmstu.rsoi.controllertests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;

import static ru.bmstu.rsoi.controllertests.AuthorControllerTest.*;
import static ru.bmstu.rsoi.controllertests.BookControllerTest.*;
import static ru.bmstu.rsoi.controllertests.LibraryVisitorControllerTest.removeLibraryVisitor;
import static ru.bmstu.rsoi.controllertests.LibraryVisitorControllerTest.visitor1;
import static ru.bmstu.rsoi.controllertests.LibraryVisitorControllerTest.visitor2;

/**
 * Created by ali on 04.12.16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RemoveDataTest {

    @Test
    public void removeBooksTest() throws IOException {
        removeBook(warAndPeace);
        removeBook(ruslanAndLudmila);
        removeLibraryVisitor(visitor1);
        removeLibraryVisitor(visitor2);
        removeAuthor(tolstoi);
        removeAuthor(pushkin);
    }
}
