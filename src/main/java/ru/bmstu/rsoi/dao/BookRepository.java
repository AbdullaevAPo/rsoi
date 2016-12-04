package ru.bmstu.rsoi.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bmstu.rsoi.entity.Book;

import java.util.List;

/**
 * Created by ali on 21.11.16.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("select b from Book b join b.authors a where " +
        "(:bookName is not null or b.name like concat('%', :bookName, '%'))" +
        "or (:authorName is not null or a.name like concat('%', :authorName, '%')) ")
    List<Book> search(@Param("bookName") String bookName,
                      @Param("authorName") String authorName,
                      Pageable pageable);
}
