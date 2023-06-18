package com.rnd.springboot3.dao;

import com.rnd.springboot3.entity.custommapping.GuitarCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuitarDao {

    @Autowired
    private EntityManager em;

    public List<GuitarCustom> findByIdCustom(long id) {
        String sql = "SELECT gd.id, g.brand_name, g.country, gd.type, gd.price, gd.is_deleted, gd.guitar_id, " +
                "gd.modified_by, gd.modified_date " +
                "FROM guitar g JOIN guitar_detail gd " +
                "ON g.id = gd.guitar_id " +
                "WHERE g.id = :id AND gd.is_deleted = false";

        Query query = em.createNativeQuery(sql, GuitarCustom.class);
        query.setParameter("id", id);
        List<GuitarCustom> result = query.getResultList();
        return result;
    }
}
