package com.rnd.springboot3.service;

import com.rnd.springboot3.*;
import com.rnd.springboot3.constant.CarConstant;
import com.rnd.springboot3.dto.*;
import com.rnd.springboot3.exception.BadRequestException;
import com.rnd.springboot3.exception.NotFoundException;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Service
public class CarGrpcService {

    public CarResponseFinalDto getCar(String id) {

        if(!isValidation(CarConstant.Regex.NUMERIC_REGEX, id)){
            List<Map<String, Object>> listError = new ArrayList<>();
            Map<String, Object> mapError = new HashMap<>();
            mapError.put(CarConstant.Response.PARAMETER, "id");
            mapError.put(CarConstant.Response.VALUE, id);
            listError.add(mapError);
            throw new BadRequestException(CarConstant.STATUS_CODE_BAD_REQUEST, CarConstant.STATUS_MESSAGE_FAILED
                    .replace(CarConstant.Response.VALUE_REPLACE, "Bad Request"),
                    listError);
        }

        CarServiceGrpc.CarServiceBlockingStub carServiceBlockingStub = initGrpcStub();
        CarResponseFinal carResponseFinal = carServiceBlockingStub.getCar(CarRequest.newBuilder()
                .setId(Long.valueOf(id)).build());

        if(carResponseFinal.getStatusCode() == CarConstant.STATUS_CODE_NOT_FOUND) {
            throw new NotFoundException(carResponseFinal.getStatusCode(), carResponseFinal.getStatusMessage());
        }

        CarDetailResponseDto carDetailResponseDto = CarDetailResponseDto.builder()
                    .id(carResponseFinal.getData().getId())
                    .manufactur(carResponseFinal.getData().getManufactur())
                    .model(carResponseFinal.getData().getModel())
                    .type(carResponseFinal.getData().getType())
                    .createdDate(carResponseFinal.getData().getCreatedDate())
                    .build();

        return CarResponseFinalDto.builder()
                .statusCode(carResponseFinal.getStatusCode())
                .statusMessage(carResponseFinal.getStatusMessage())
                .data(carDetailResponseDto)
                .build();
    }


    public CarResponseListDto getCarList() {
        CarServiceGrpc.CarServiceBlockingStub carServiceBlockingStub =  initGrpcStub();
        CarResponseList carResponseList = carServiceBlockingStub.getCarList(CarRequest.newBuilder().build());

        if(carResponseList.getStatusCode() == CarConstant.STATUS_CODE_NOT_FOUND) {
            throw new NotFoundException(carResponseList.getStatusCode(), carResponseList.getStatusMessage());
        }

        List<CarDetailResponseDto> dataList = new ArrayList<>();
        for(CarResponse carResponse : carResponseList.getDataListList()) {
            CarDetailResponseDto data = mappingCarDetailResponseDto(carResponse);
            dataList.add(data);
        }

        return CarResponseListDto.builder()
                .statusCode(carResponseList.getStatusCode())
                .statusMessage(carResponseList.getStatusMessage())
                .dataList(dataList)
                .build();
    }

    public CarResponseWithPageDto getCarWithPage(CarRequestDto carRequestDto) {

        List<Map<String, Object>> listError = new ArrayList<>();
        for(Field f : carRequestDto.getClass().getDeclaredFields()) {
            Map<String, Object> mapError = new HashMap<>();

            if (f.getName().equals("rowNumber")) {
                if (!isValidation(CarConstant.Regex.NUMERIC_REGEX, carRequestDto.getRowNumber())) {
                    mapError.put(CarConstant.Response.PARAMETER, f.getName());
                    mapError.put(CarConstant.Response.VALUE, carRequestDto.getRowNumber());
                    listError.add(mapError);
                }
            } else if (f.getName().equals("rowPerPage")){
                if(!isValidation(CarConstant.Regex.NUMERIC_REGEX, carRequestDto.getRowPerPage())) {
                    mapError.put(CarConstant.Response.PARAMETER, f.getName());
                    mapError.put(CarConstant.Response.VALUE, carRequestDto.getRowPerPage());
                    listError.add(mapError);
                }
            }
        }

        if(!listError.isEmpty()) {
            throw new BadRequestException(CarConstant.STATUS_CODE_BAD_REQUEST, CarConstant.STATUS_MESSAGE_FAILED
                    .replace(CarConstant.Response.VALUE_REPLACE, "Bad Request"),
                    listError);
        }


        CarServiceGrpc.CarServiceBlockingStub carServiceBlockingStub =  initGrpcStub();
        CarResponseWithPage carResponseList = carServiceBlockingStub.getCarWithPage(CarRequestWithPage.newBuilder()
                        .setKey(carRequestDto.getKey())
                        .setRowNumber(Integer.valueOf(carRequestDto.getRowNumber()))
                        .setRowPerPage(Integer.valueOf(carRequestDto.getRowPerPage()))
                .build());

        if(carResponseList.getStatusCode() == CarConstant.STATUS_CODE_NOT_FOUND) {
            throw new NotFoundException(carResponseList.getStatusCode(), carResponseList.getStatusMessage());
        }

        List<CarDetailResponseDto> dataList = new ArrayList<>();
        for(CarResponse carResponse : carResponseList.getDataListList()) {
            CarDetailResponseDto data = mappingCarDetailResponseDto(carResponse);
            dataList.add(data);
        }

        return CarResponseWithPageDto.builder()
                .rowPerPage(carResponseList.getRowPerPage())
                .rowNumber(carResponseList.getRowNumber())
                .totalData(carResponseList.getTotalData())
                .statusCode(carResponseList.getStatusCode())
                .statusMessage(carResponseList.getStatusMessage())
                .dataList(dataList)
                .build();
    }

    private CarServiceGrpc.CarServiceBlockingStub initGrpcStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        return CarServiceGrpc.newBlockingStub(channel);
    }

    private boolean isValidation(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).matches() ? true : false;
    }

    private CarDetailResponseDto mappingCarDetailResponseDto(CarResponse carResponse) {
        return CarDetailResponseDto.builder()
                .id(carResponse.getId())
                .manufactur(carResponse.getManufactur())
                .model(carResponse.getModel())
                .type(carResponse.getType())
                .createdDate(carResponse.getCreatedDate())
                .build();
    }

}
