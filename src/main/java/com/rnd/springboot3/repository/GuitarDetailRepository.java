package com.rnd.springboot3.repository;

import com.rnd.springboot3.entity.Guitar;
import com.rnd.springboot3.entity.GuitarDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuitarDetailRepository extends JpaRepository<GuitarDetail, Long> {

    @Query(value = "SELECT * FROM guitar_detail gd WHERE gd.guitar_id = ?1 AND gd.is_deleted = false", nativeQuery = true)
    List<GuitarDetail> findGuitarDetailByGuitarId(long guitarIdd);
}
