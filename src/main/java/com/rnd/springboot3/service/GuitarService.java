package com.rnd.springboot3.service;

import com.rnd.springboot3.constant.CarConstant;
import com.rnd.springboot3.dao.GuitarDao;
import com.rnd.springboot3.dto.GuitarDetailRequest;
import com.rnd.springboot3.dto.GuitarDetailResponse;
import com.rnd.springboot3.dto.GuitarRequest;
import com.rnd.springboot3.dto.GuitarResponse;
import com.rnd.springboot3.entity.Guitar;
import com.rnd.springboot3.entity.GuitarDetail;
import com.rnd.springboot3.entity.custommapping.GuitarCustom;
import com.rnd.springboot3.exception.NotFoundException;
import com.rnd.springboot3.repository.GuitarDetailRepository;
import com.rnd.springboot3.repository.GuitarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

@Service
public class GuitarService {

    @Autowired
    private GuitarRepository guitarRepository;
    @Autowired
    private GuitarDetailRepository guitarDetailRepository;
    @Autowired
    private GuitarDao guitarDao;

    @Transactional
    public Map<String, Object> create(GuitarRequest guitarRequest) {
        Map<String, Object> result = new HashMap<>();

        Guitar guitar = Guitar.builder()
                .brandName(guitarRequest.getBrandName())
                .country(guitarRequest.getCountry())
                .cretedDate(new Date())
                .createdBy("AUTO")
                .modifiedDate(new Date())
                .modifiedBy("AUTO")
                .isDeleted(false)
                .build();
        Guitar savedGuitar = guitarRepository.save(guitar);

        List<GuitarDetail> guitarDetailList = new ArrayList<>();
        for(GuitarDetailRequest gdr : guitarRequest.getGuitarDetailRequestList()) {
            GuitarDetail guitarDetail = GuitarDetail.builder()
                    .type(gdr.getType())
                    .price(gdr.getPrice())
                    .guitar(savedGuitar)
                    .createdDate(new Date())
                    .createdBy("AUTO")
                    .modifiedDate(new Date())
                    .modifiedBy("AUTO")
                    .isDeleted(false)
                    .build();
            guitarDetailList.add(guitarDetail);
        }
        guitarDetailRepository.saveAll(guitarDetailList);

        result.put("responseCode", CarConstant.STATUS_CODE_SUCCESS);
        result.put("responseMessage", CarConstant.STATUS_MESSAGE_SUCCESS);
        return result;
    }

    public Map<String, Object> getDetailGuitarUsingJpa(long id) {
        Map<String, Object> result = new HashMap<>();

        List<GuitarDetail> guitarDetailList = guitarDetailRepository.findGuitarDetailByGuitarId(id);
        if(guitarDetailList.isEmpty()) {
            throw new NotFoundException(CarConstant.STATUS_CODE_NOT_FOUND, "Data Not Found!");
        }

        List<GuitarDetailResponse> guitarDetailResponseList = new ArrayList<>();
        for(GuitarDetail gd : guitarDetailList) {
            GuitarDetailResponse guitarDetailResponse = GuitarDetailResponse.builder()
                    .id(gd.getId())
                    .type(gd.getType())
                    .price(gd.getPrice())
                    .craetedDate(gd.getCreatedDate())
                    .createdBy(gd.getCreatedBy())
                    .modifiedDate(gd.getModifiedDate())
                    .modifiedBy(gd.getModifiedBy())
                    .build();
            guitarDetailResponseList.add(guitarDetailResponse);
        }

        GuitarResponse guitarResponse = GuitarResponse.builder()
                .id(guitarDetailList.get(0).getGuitar().getId())
                .brandName(guitarDetailList.get(0).getGuitar().getBrandName())
                .country(guitarDetailList.get(0).getGuitar().getCountry())
                .craetedDate(guitarDetailList.get(0).getGuitar().getCretedDate())
                .createdBy(guitarDetailList.get(0).getGuitar().getCreatedBy())
                .modifiedBy(guitarDetailList.get(0).getGuitar().getModifiedBy())
                .modifiedDate(guitarDetailList.get(0).getGuitar().getModifiedDate())
                .isDeleted(guitarDetailList.get(0).getGuitar().isDeleted())
                .guitarDetailResponseList(guitarDetailResponseList)
                .build();

        result.put("statusCode", CarConstant.STATUS_CODE_SUCCESS);
        result.put("statusMessage", CarConstant.STATUS_MESSAGE_SUCCESS);
        result.put("data", guitarResponse);
        return result;
    }

    public Map<String, Object> getDetailGuitarDao(long id) {
        Map<String, Object> result = new HashMap<>();

        List<GuitarCustom> guitarCustomList = guitarDao.findByIdCustom(id);
        if(guitarCustomList.isEmpty()) {
            throw new NotFoundException(CarConstant.STATUS_CODE_NOT_FOUND, "Data Not Found!");
        }

        List<GuitarDetailResponse> guitarDetailResponseList = new ArrayList<>();
        for(GuitarCustom gc : guitarCustomList) {
            GuitarDetailResponse guitarDetailResponse = GuitarDetailResponse.builder()
                    .id(gc.getId())
                    .type(gc.getType())
                    .price(gc.getPrice())
                    .modifiedDate(gc.getModifiedDate())
                    .modifiedBy(gc.getModifiedBy())
                    .build();
            guitarDetailResponseList.add(guitarDetailResponse);
        }

        GuitarResponse guitarResponse = GuitarResponse.builder()
                .id(guitarCustomList.get(0).getGuitarId())
                .brandName(guitarCustomList.get(0).getBrandName())
                .country(guitarCustomList.get(0).getCountry())
                .guitarDetailResponseList(guitarDetailResponseList)
                .build();

        result.put("statusCode", CarConstant.STATUS_CODE_SUCCESS);
        result.put("statusMessage", CarConstant.STATUS_MESSAGE_SUCCESS);
        result.put("data", guitarResponse);
        return result;
    }

}
