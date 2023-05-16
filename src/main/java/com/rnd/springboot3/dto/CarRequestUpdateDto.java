package com.rnd.springboot3.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarRequestUpdateDto {

    private String id;
    private String manufactur;
    private String model;
    private String type;

}
