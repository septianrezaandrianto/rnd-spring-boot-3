package com.rnd.springboot3.dao;

import com.rnd.springboot3.entity.custommapping.GuitarCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
public class GuitarDaoTest {

    @Mock
    private EntityManager em;
    @Mock
    private Query query;
    @InjectMocks
    private GuitarDao guitarDao;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should return list of GuitarCustom objects when findByIdCustom is called")
    void findByIdCustomShouldReturnListOfGuitarCustom() {
        List<GuitarCustom> expectedResult = new ArrayList<>();

        when(em.createNativeQuery(anyString(), eq(GuitarCustom.class))).thenReturn(query);
        when(query.setParameter(eq("id"), eq(1L))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);

        List<GuitarCustom> result = guitarDao.findByIdCustom(1L);

        verify(em, times(1)).createNativeQuery(anyString(), eq(GuitarCustom.class));
        verify(query, times(1)).setParameter(eq("id"), eq(1L));
        verify(query, times(1)).getResultList();

        assertEquals(expectedResult, result);
    }
}
