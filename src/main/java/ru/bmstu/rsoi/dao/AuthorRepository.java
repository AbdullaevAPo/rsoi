package ru.bmstu.rsoi.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import ru.bmstu.rsoi.entity.Author;
import java.util.Date;
import java.util.List;

/**
 * Created by ali on 20.11.16.
 */
@Repository
public interface AuthorRepository
    extends JpaRepository<Author, Integer> {

    @Query("select s from Author s join fetch s.books b " +
        " where (:name is null or s.name like concat('%', :name, '%')) " +
        " and (:firstDate is null or s.birthDate >= :firstDate)" +
        " and (:secondDate is null or s.birthDate <= :secondDate)" +
        " and (:bookName is null or b.name like concat('%', :bookName, '%'))" +
        " order by s.name asc, s.birthDate desc")
    List<Author> search(@Param("name") String name,
                        @Param("firstDate") Date firstDate,
                        @Param("secondDate" ) Date secondDate,
                        @Param("bookName") String bookName,
                        Pageable pageable);
}
