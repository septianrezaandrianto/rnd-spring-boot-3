package com.rnd.springboot3;

import com.rnd.springboot3.dto.GuitarDetailRequest;
import com.rnd.springboot3.dto.GuitarRequest;
import com.rnd.springboot3.entity.Guitar;
import com.rnd.springboot3.entity.GuitarDetail;
import com.rnd.springboot3.entity.custommapping.GuitarCustom;
import com.rnd.springboot3.exception.NotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    private static Utils instance;
    private Utils() {}

    // Public getInstance() method to get the singleton instance
    public static Utils getInstance() {
        if (instance == null) {
            synchronized (Utils.class) {
                if (instance == null) {
                    instance = new Utils();
                }
            }
        }
        return instance;
    }


    public Guitar guitar(String brandName, String country) {
        return Guitar.builder()
                .id(1L)
                .brandName(brandName)
                .country(country)
                .createdBy("AUTO")
                .cretedDate(new Date())
                .modifiedBy("AUTO")
                .modifiedDate(new Date())
                .isDeleted(false)
                .build();
    }

    public GuitarDetail guitarDetail(String type, BigDecimal price, String brandName, String country) {
        return GuitarDetail.builder()
                .id(0L)
                .type(type)
                .price(price)
                .createdBy("AUTO")
                .createdDate(new Date())
                .modifiedBy("AUTO")
                .modifiedDate(new Date())
                .isDeleted(false)
                .guitar(guitar(brandName, country))
                .build();
    }

    public List<GuitarDetail> guitarDetailList(GuitarRequest guitarRequest) {
        List<GuitarDetail> guitarDetailList = new ArrayList<>();
        for(GuitarDetailRequest gdr : guitarRequest.getGuitarDetailRequestList()) {
            guitarDetailList.add(guitarDetail(gdr.getType(), gdr.getPrice(),
                    guitarRequest.getBrandName(), guitarRequest.getCountry()));
        }
        return guitarDetailList;
    }

    public NotFoundException notFoundException(int statusCode, String statusMessage) {
        NotFoundException notFoundException = new NotFoundException(statusCode, statusMessage);
        return notFoundException;
    }

    public GuitarCustom guitarCustom(String brandName, String country, String type, BigDecimal price, Date modifiedDate) {
        GuitarCustom guitarCustom = new GuitarCustom();
        guitarCustom.setCountry(country);
        guitarCustom.setBrandName(brandName);
        guitarCustom.setType(type);
        guitarCustom.setPrice(price);
        guitarCustom.setIsDeleted(false);
        guitarCustom.setModifiedDate(modifiedDate);
        guitarCustom.setModifiedBy("AUTO");
        return guitarCustom;
    }

    public List<GuitarCustom> guitarCustomList(GuitarRequest guitarRequest) {
        List<GuitarCustom> guitarCustomList = new ArrayList<>();
        for(GuitarDetailRequest gdr : guitarRequest.getGuitarDetailRequestList()) {
            guitarCustomList.add(guitarCustom(guitarRequest.getBrandName(), guitarRequest.getCountry(), gdr.getType(),
                    gdr.getPrice(), new Date()));
        }
        return guitarCustomList;
    }

    public GuitarRequest guitarRequest() {
        return GuitarRequest.builder()
                .brandName("Ibanez")
                .country("Indonesia")
                .guitarDetailRequestList(guitarDetailRequestList())
                .build();
    }

    public List<GuitarDetailRequest> guitarDetailRequestList() {
        List<GuitarDetailRequest> guitarDetailRequestList = new ArrayList<>();
        guitarDetailRequestList.add(guitarDetailRequest("RGT6EX", BigDecimal.valueOf(1000)));
        guitarDetailRequestList.add(guitarDetailRequest("RGA87H", BigDecimal.valueOf(500)));
        return guitarDetailRequestList;

    }
    public GuitarDetailRequest guitarDetailRequest(String type, BigDecimal price) {
        return GuitarDetailRequest.builder()
                .type(type)
                .price(price)
                .build();
    }
}
