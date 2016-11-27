package ru.bmstu.rsoi.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bmstu.rsoi.entity.BookInstance;

import java.util.List;

/**
 * Created by ali on 23.11.16.
 */
@Repository
public interface BookInstanceRepository extends JpaRepository<BookInstance, Integer> {
    @Query("select b from BookInstance b where b.book.id = :bookId")
    List<BookInstance> findAvailableBookInstances(@Param("bookId") int bookId);
}
