package com.rnd.springboot3.dto;

import com.rnd.springboot3.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarResponseDto {

    private List<Car> carList;
    private Integer rowPerpage;
    private Integer rowNumber;
    private Integer totalData;

}
