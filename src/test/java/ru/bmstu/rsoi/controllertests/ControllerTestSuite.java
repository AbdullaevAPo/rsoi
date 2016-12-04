package ru.bmstu.rsoi.controllertests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.bmstu.rsoi.web.AuthorController;

/**
 * Created by ali on 03.12.16.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {
    AuthorControllerTest.class,
    LibraryVisitorControllerTest.class,
    BookControllerTest.class
})
public class ControllerTestSuite {
}
