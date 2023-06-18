package com.rnd.springboot3.repository;

import com.rnd.springboot3.entity.Guitar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GuitarRepository extends JpaRepository<Guitar, Long> {

    @Query(value = "SELECT * FROM guitar g WHERE g.id = ?1 AND g.is_deleted = false", nativeQuery = true)
    Guitar findGuitarById(long id);
}
