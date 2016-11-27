package ru.bmstu.rsoi.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.bmstu.rsoi.ApplicationConfig;
import ru.bmstu.rsoi.entity.Author;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by ali on 20.11.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ApplicationConfig.class})
@Transactional
@WebAppConfiguration
public class AuthorTest {

    public static Logger logger = Logger.getLogger(AuthorTest.class.getName());

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void test() {
        Author author = new Author("Пушкин", new Date());
        authorRepository.save(author);
        logger.info(authorRepository.findAll().toString());
    }
}
