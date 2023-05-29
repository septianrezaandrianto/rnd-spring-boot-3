package com.rnd.springboot3.repository;

import com.rnd.springboot3.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(value = "SELECT * FROM CAR WHERE CREATED_DATE >= ?1 AND MANUFACTUR = ?2 ORDER BY ID ASC",
            nativeQuery = true)
    List<Car> getCarList(Date createdDate, String manufactur);

    @Query(value = "SELECT * FROM CAR WHERE MODEL = ?1", nativeQuery = true)
    Car getCarByModel(String model);

}
