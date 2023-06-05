package com.rnd.springboot3.dao;

import com.google.gson.Gson;
import com.rnd.springboot3.CarResponse;
import com.rnd.springboot3.CarUpdateRequest;
import com.rnd.springboot3.dto.CarResponseDto;
import com.rnd.springboot3.entity.Car;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CarDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarDao.class);

    private EntityManager createEm() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("springboot3"); //NOSONAR
        return emf.createEntityManager();
    }

    public Car getCarById(long id) {
        EntityManager em = createEm();

        Car car = em.find(Car.class, id);
//        if(car == null) {
//            throw new NoSuchElementException("Data with ID ".concat(String.valueOf(id)).concat(" not found"));
//        }
        return car;
    }

    public List<Car> getCarList() {
        EntityManager em = createEm();

        Query query = em.createNativeQuery("SELECT * FROM CAR ORDER BY id", Car.class);
        List<Car> carList = query.getResultList();
        return carList;
    }

    public Page<Car> getCarListWithPage(int rowPerPage, int rowNumber, String key) {
        EntityManager em = createEm();

        int pageNumber = rowNumber > 0 ? rowNumber : 1;
        int pageSize = rowPerPage;
        String keyword = convertRequest(key);

        Pageable pageable = PageRequest.of(pageSize, pageNumber);

        Query query = em.createQuery("FROM Car WHERE manufactur LIKE :keyword ", Car.class);
        query.setParameter("keyword", "%".concat(keyword).concat("%"));

        if (rowNumber == 0) {
            query.setFirstResult(rowNumber);
        } else {
            query.setFirstResult((pageNumber) * pageSize);
        }
        query.setMaxResults(pageSize);
        List <Car> carList = query.getResultList();

        Query queryTotal = em.createQuery("SELECT COUNT(id) FROM Car");
        long countResult = (long)queryTotal.getSingleResult();
        int size = (int)countResult;

        return new PageImpl<>(carList, pageable,size);
    }

    private String convertRequest(String value) {
        return value != null ? value : "";
    }

    public CarResponseDto getCarListWithManualPage(int rowPerPage, int rowNumber, String key) {
        EntityManager em = createEm();
        String keyword = convertRequest(key);

        Query query = em.createNativeQuery("SELECT * FROM CAR WHERE manufactur LIKE :key " +
                "ORDER BY id ASC LIMIT :rowPerPage OFFSET :rowNumber", Car.class);
        query.setParameter("key", "%".concat(keyword).concat("%"));
        query.setParameter("rowNumber", rowNumber );
        query.setParameter("rowPerPage", rowPerPage);

        List<Car> carList = query.getResultList();

        return CarResponseDto.builder()
                .carList(carList)
                .rowNumber(rowNumber)
                .rowPerpage(rowPerPage)
                .totalData(carList.size())
                .build();
    }

    public void insertCar(Car car) {
        EntityManager em = createEm();

        String query = "INSERT INTO CAR (manufactur, model, type, created_date) " +
                "VALUES (:manufactur, :model, :type, :created_date)";

        em.getTransaction().begin();
        em.createNativeQuery(query)
                .setParameter("manufactur", car.getManufactur())
                .setParameter("model", car.getModel())
                .setParameter("type", car.getType())
                .setParameter("created_date", car.getCreatedDate())
            .executeUpdate();
        em.getTransaction().commit();
    }

    public void deleteCar(long id) {
        EntityManager em = createEm();

        em.getTransaction().begin();
        em.createNativeQuery("DELETE FROM CAR WHERE ID = :id")
                .setParameter("id", id)
            .executeUpdate();
        em.getTransaction().commit();
    }

    public void updateCar(CarResponse request) {
        EntityManager em = createEm();
        String query = "UPDATE CAR SET manufactur = :manufactur, model = :model, type = :type WHERE id = :id";

        em.getTransaction().begin();
        em.createNativeQuery(query)
                .setParameter("manufactur", request.getManufactur())
                .setParameter("model", request.getModel())
                .setParameter("type", request.getType())
                .setParameter("id", request.getId())
                .executeUpdate();
        em.getTransaction().commit();
    }


}
