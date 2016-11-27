package ru.bmstu.rsoi.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bmstu.rsoi.entity.Author;
import java.util.Date;
import java.util.List;

/**
 * Created by ali on 20.11.16.
 */
@Repository
public interface AuthorRepository
    extends JpaRepository<Author, Integer> {

    @Query("select s from Author s where s.name like concat('%', :name, '%')" +
        " and (:firstDate is not null or s.birthDate > :firstDate)" +
        " and (:secondDate is not null or s.birthDate < :secondDate)")
    List<Author> search(@Param("name") String name,
                        @Param("firstDate") Date firstDate,
                        @Param("secondDate") Date secondDate,
                        Pageable pageable);

    @Override
    @Query("select s from Author s join fetch s.books where s.id = :personId")
    Author findOne(@Param("personId") Integer personId);
}
