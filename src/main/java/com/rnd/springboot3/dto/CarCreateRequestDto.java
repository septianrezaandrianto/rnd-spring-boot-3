package com.rnd.springboot3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarCreateRequestDto {

    private String manufactur;
    private String model;
    private String type;

}
