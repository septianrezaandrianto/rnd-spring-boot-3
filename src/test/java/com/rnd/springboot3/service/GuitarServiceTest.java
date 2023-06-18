package com.rnd.springboot3.service;

import com.rnd.springboot3.Utils;
import com.rnd.springboot3.constant.CarConstant;
import com.rnd.springboot3.dao.GuitarDao;
import com.rnd.springboot3.entity.GuitarDetail;
import com.rnd.springboot3.exception.NotFoundException;
import com.rnd.springboot3.repository.GuitarDetailRepository;
import com.rnd.springboot3.repository.GuitarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
public class GuitarServiceTest {

    @Mock
    private GuitarRepository guitarRepository;
    @Mock
    private GuitarDetailRepository guitarDetailRepository;
    @Mock
    private GuitarDao guitarDao;

    @InjectMocks GuitarService guitarService;

    @Test
    @DisplayName("should return success create service")
    void create() {
        when(guitarRepository.save(Utils.getInstance().guitar(Utils.getInstance().guitarRequest().getBrandName(),
                Utils.getInstance().guitarRequest().getCountry())))
                    .thenReturn(Utils.getInstance().guitar(Utils.getInstance().guitarRequest().getBrandName(),
                        Utils.getInstance().guitarRequest().getCountry()));
        Map<String, Object> result = guitarService.create(Utils.getInstance().guitarRequest());

        ArgumentCaptor<List<GuitarDetail>> guitarDetailListCaptor = ArgumentCaptor.forClass(List.class);
        verify(guitarDetailRepository, times(1)).saveAll(guitarDetailListCaptor.capture());
        List<GuitarDetail> capturedList = guitarDetailListCaptor.getValue();

        assertEquals(CarConstant.STATUS_CODE_SUCCESS, result.get("responseCode"));
        assertEquals(CarConstant.STATUS_MESSAGE_SUCCESS, result.get("responseMessage"));
        assertEquals(Utils.getInstance().guitarRequest().getGuitarDetailRequestList().size(), capturedList.size());
    }

    @Test
    @DisplayName("should return success getDetailGuitarUsingJpa service")
    void getDetailGuitarUsingJpa() {
        when(guitarDetailRepository.findGuitarDetailByGuitarId(1L))
                .thenReturn(Utils.getInstance().guitarDetailList(Utils.getInstance().guitarRequest()));
        Map<String, Object> result = guitarService.getDetailGuitarUsingJpa(1L);
        assertNotNull(result);
    }


    @Test
    @DisplayName("should return success getDetailGuitarUsingJpa_notFound service")
    void getDetailGuitarUsingJpa_notFound() {
        NotFoundException response = assertThrows(NotFoundException.class, () -> {
            guitarService.getDetailGuitarUsingJpa(0L);
        });
        assertEquals(Utils.getInstance().notFoundException(CarConstant.STATUS_CODE_NOT_FOUND,
                "Data Not Found!"), response);
    }

    @Test
    @DisplayName("should return success getDetailGuitarDao service")
    void getDetailGuitarDao() {
        when(guitarDao.findByIdCustom(1L)).thenReturn(Utils.getInstance().guitarCustomList(Utils.getInstance().guitarRequest()));
        Map<String, Object> result = guitarService.getDetailGuitarDao(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("should return success getDetailGuitarDao_notFound service")
    void getDetailGuitarDao_notFound() {
        NotFoundException response = assertThrows(NotFoundException.class, () -> {
            guitarService.getDetailGuitarDao(0L);
        });
        assertEquals(Utils.getInstance().notFoundException(CarConstant.STATUS_CODE_NOT_FOUND,
                "Data Not Found!"), response);
    }


}
