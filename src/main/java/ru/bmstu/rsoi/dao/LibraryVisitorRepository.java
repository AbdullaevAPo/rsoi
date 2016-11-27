package ru.bmstu.rsoi.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bmstu.rsoi.entity.LibraryVisitor;
import ru.bmstu.rsoi.entity.LibraryVisitor;

import java.util.Date;
import java.util.List;

/**
 * Created by ali on 21.11.16.
 */
@Repository
public interface LibraryVisitorRepository extends JpaRepository<LibraryVisitor, Integer>{
    @Query("select s from LibraryVisitor s where s.name like concat('%', :name, '%')" +
        " and (:firstDate is not null or s.birthDate > :firstDate)" +
        " and (:secondDate is not null or s.birthDate < :secondDate)")
    List<LibraryVisitor> search(@Param("name") String name,
                                @Param("firstDate") Date firstDate,
                                @Param("secondDate") Date secondDate,
                                Pageable pageable);

    @Override
    @Query("select s from LibraryVisitor s join fetch s.bookList where s.id = :personId")
    LibraryVisitor findOne(@Param("personId") Integer personId);
}
