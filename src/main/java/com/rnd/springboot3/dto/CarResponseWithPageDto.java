package com.rnd.springboot3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarResponseWithPageDto {

    private int statusCode;
    private String statusMessage;
    private int rowPerPage;
    private int rowNumber;
    private int totalData;
    private List<CarDetailResponseDto> dataList;
}
