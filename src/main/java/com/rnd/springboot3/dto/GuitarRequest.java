package com.rnd.springboot3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuitarRequest {

    private String brandName;
    private String country;
    private List<GuitarDetailRequest> guitarDetailRequestList;

}
