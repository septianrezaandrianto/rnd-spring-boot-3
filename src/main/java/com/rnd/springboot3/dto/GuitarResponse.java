package com.rnd.springboot3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuitarResponse {

    private long id;
    private String brandName;
    private String country;
    private Boolean isDeleted;
    private Date craetedDate;
    private String createdBy;
    private Date modifiedDate;
    private String modifiedBy;
    private List<GuitarDetailResponse> guitarDetailResponseList;

}
