package com.rnd.springboot3.grpc;

import com.rnd.springboot3.*;
import com.rnd.springboot3.constant.CarConstant;
import com.rnd.springboot3.dao.CarDao;
import com.rnd.springboot3.dto.CarResponseDto;
import com.rnd.springboot3.entity.Car;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@GrpcService
public class CarGrpc extends CarServiceGrpc.CarServiceImplBase {

    private SimpleDateFormat dateDisplay = new SimpleDateFormat("dd/MM/yyyy");

    private CarDao carDao = new CarDao();
    /**
     * Unary operation to get the car based on id
     * @param request
     * @param responseObserver
     */


    @Override
    public void getCar(CarRequest request, StreamObserver<CarResponseFinal> responseObserver) {

        CarResponseFinal.Builder response = CarResponseFinal.newBuilder();
        Car car = carDao.getCarById(request.getId());
        if(car == null) {
            response = mappingResponseGetCar(CarConstant.STATUS_CODE_NOT_FOUND,
                    CarConstant.STATUS_MESSAGE_FAILED.replace(CarConstant.Response.VALUE_REPLACE,
                            "Data with ID ".concat(String.valueOf(request.getId())).concat(" not found!")),
                    false, null);

        } else {
            CarResponse carResponse = CarResponse.newBuilder()
                    .setId(request.getId())
                    .setManufactur(car.getManufactur())
                    .setModel(car.getModel())
                    .setType(car.getType())
                    .setCreatedDate(dateDisplay.format(car.getCreatedDate()))
                    .build();

            response = mappingResponseGetCar(CarConstant.STATUS_CODE_SUCCESS, CarConstant.STATUS_MESSAGE_SUCCESS,
                    true, carResponse);
        }

        //set the response object
        responseObserver.onNext(response.build());
        //mark process is completed
        responseObserver.onCompleted();
    }

    private CarResponseFinal.Builder mappingResponseGetCar(int statusCode, String statusMessage, boolean isSuccess,
                                                     CarResponse carResponse) {
        if(!isSuccess) {
            return CarResponseFinal.newBuilder()
                    .setStatusCode(statusCode)
                    .setStatusMessage(statusMessage);
        } else {
            return CarResponseFinal.newBuilder()
                    .setStatusCode(statusCode)
                    .setStatusMessage(statusMessage)
                    .setData(carResponse);
        }
    }

    @Override
    public void getCarList(CarRequest request, StreamObserver<CarResponseList> responseObserver) {
        CarResponseList.Builder carBuilder = CarResponseList.newBuilder();
        CarResponseList.Builder result = CarResponseList.newBuilder();

        List<Car> carList = carDao.getCarList();
        if(!carList.isEmpty()) {
            for (Car car : carList) {
                carBuilder.addDataList(CarResponse.newBuilder()
                        .setId(car.getId())
                        .setManufactur(car.getManufactur())
                        .setModel(car.getModel())
                        .setType(car.getType())
                        .setCreatedDate(dateDisplay.format(car.getCreatedDate()))
                        .build()
                );
            }

            result = carBuilder
                    .setStatusCode(CarConstant.STATUS_CODE_SUCCESS)
                    .setStatusMessage(CarConstant.STATUS_MESSAGE_SUCCESS);
        } else {
            result = CarResponseList.newBuilder()
                    .setStatusCode(CarConstant.STATUS_CODE_NOT_FOUND)
                    .setStatusMessage(CarConstant.STATUS_MESSAGE_FAILED.replace(CarConstant.Response.VALUE_REPLACE,
                            "Data is empty!"));
        }

        responseObserver.onNext(result.build());
        responseObserver.onCompleted();
    }


    @Override
    public void getCarWithPage(CarRequestWithPage request, StreamObserver<CarResponseWithPage> responseObserver) {
        CarResponseWithPage.Builder carBuilder = CarResponseWithPage.newBuilder();
        CarResponseWithPage.Builder result = CarResponseWithPage.newBuilder();

        Page<Car> carPage = carDao.getCarListWithPage(request.getRowPerPage(), request.getRowNumber(), request.getKey());
        if(!carPage.isEmpty()) {
            for (Car car : carPage.getContent()) {
                carBuilder.addDataList(CarResponse.newBuilder()
                        .setId(car.getId())
                        .setManufactur(car.getManufactur())
                        .setModel(car.getModel())
                        .setType(car.getType())
                        .setCreatedDate(dateDisplay.format(car.getCreatedDate()))
                        .build()
                );
            }

            result = carBuilder
                    .setStatusCode(CarConstant.STATUS_CODE_SUCCESS)
                    .setStatusMessage(CarConstant.STATUS_MESSAGE_SUCCESS)
                    .setRowNumber(request.getRowNumber())
                    .setRowPerPage(request.getRowPerPage())
                    .setTotalData((int) carPage.getTotalElements());
        } else {
            result = carBuilder
                    .setStatusCode(CarConstant.STATUS_CODE_NOT_FOUND)
                    .setStatusMessage(CarConstant.STATUS_MESSAGE_FAILED.replace(CarConstant.Response.VALUE_REPLACE,
                            "Data is empty!"))
                    .setRowNumber(request.getRowNumber())
                    .setRowPerPage(request.getRowPerPage())
                    .setTotalData((int) carPage.getTotalElements());
        }
        responseObserver.onNext(result.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getCarWithManualPage(CarRequestWithPage request, StreamObserver<CarResponseWithPage> responseObserver) {
        CarResponseWithPage.Builder carBuilder = CarResponseWithPage.newBuilder();
        CarResponseWithPage.Builder result = CarResponseWithPage.newBuilder();

        CarResponseDto carResponseDto = carDao.getCarListWithManualPage(request.getRowPerPage(),
                request.getRowNumber(), request.getKey());
        if(!carResponseDto.getCarList().isEmpty()) {
            for(Car car : carResponseDto.getCarList()) {
                carBuilder.addDataList(CarResponse.newBuilder()
                        .setId(car.getId())
                        .setManufactur(car.getManufactur())
                        .setModel(car.getModel())
                        .setType(car.getType())
                        .setCreatedDate(dateDisplay.format(car.getCreatedDate()))
                        .build()
                );
            }

            result = carBuilder
                    .setStatusCode(CarConstant.STATUS_CODE_SUCCESS)
                    .setStatusMessage(CarConstant.STATUS_MESSAGE_SUCCESS)
                    .setRowNumber(carResponseDto.getRowNumber())
                    .setRowPerPage(carResponseDto.getRowPerpage())
                    .setTotalData(carResponseDto.getTotalData());
        } else {
            result = carBuilder
                    .setStatusCode(CarConstant.STATUS_CODE_NOT_FOUND)
                    .setStatusMessage(CarConstant.STATUS_MESSAGE_FAILED.replace(CarConstant.Response.VALUE_REPLACE,
                            "Data is empty!"))
                    .setRowNumber(carResponseDto.getRowNumber())
                    .setRowPerPage(carResponseDto.getRowPerpage())
                    .setTotalData(carResponseDto.getTotalData());
        }
        responseObserver.onNext(result.build());
        responseObserver.onCompleted();
    }

    @Override
    public void createCar(CarCreateRequest request, StreamObserver<CarResponseFinal> responseObserver) {
        Car car = Car.builder()
            .manufactur(request.getManufactur())
            .model(request.getModel())
            .type(request.getType())
            .createdDate(new Date())
            .build();

        carDao.insertCar(car);

        CarResponse carResponse = CarResponse.newBuilder()
                .setManufactur(car.getManufactur())
                .setModel(car.getModel())
                .setType(car.getType())
                .setCreatedDate(dateDisplay.format(car.getCreatedDate()))
                .build();

        CarResponseFinal response = CarResponseFinal.newBuilder()
                .setStatusCode(CarConstant.STATUS_CODE_SUCCESS)
                .setStatusMessage(CarConstant.STATUS_MESSAGE_SUCCESS)
                .setData(carResponse)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    @Override
    public void deleteCar(CarRequest request, StreamObserver<CarResponseFinal> responseObserver) {
        CarResponseFinal.Builder response = CarResponseFinal.newBuilder();

        Car car = carDao.getCarById(request.getId());
        if(car == null) {
            response = mappingResponseGetCar(CarConstant.STATUS_CODE_NOT_FOUND,
                    CarConstant.STATUS_MESSAGE_FAILED.replace(CarConstant.Response.VALUE_REPLACE,
                            "Data with ID ".concat(String.valueOf(request.getId())).concat(" not found!")),
                    false, null);

        } else {
            CarResponse carResponse = CarResponse.newBuilder()
                        .setId(car.getId())
                        .setManufactur(car.getManufactur())
                        .setModel(car.getModel())
                        .setType(car.getType())
                        .setCreatedDate(dateDisplay.format(car.getCreatedDate()))
                    .build();
            carDao.deleteCar(request.getId());

            response = CarResponseFinal.newBuilder()
                    .setStatusCode(CarConstant.STATUS_CODE_SUCCESS)
                    .setStatusMessage(CarConstant.STATUS_MESSAGE_SUCCESS)
                    .setData(carResponse);
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateCar(CarUpdateRequest request, StreamObserver<CarResponseFinal> responseObserver) {
        CarResponse.Builder carResponse = CarResponse.newBuilder();
        CarResponseFinal.Builder response = CarResponseFinal.newBuilder();

        Car car = carDao.getCarById(request.getId());
        if(car != null) {
            carResponse = CarResponse.newBuilder()
                    .setId(request.getId())
                    .setManufactur(request.getManufactur())
                    .setType(request.getType())
                    .setModel(request.getModel())
                    .setCreatedDate(dateDisplay.format(car.getCreatedDate()));
            carDao.updateCar(carResponse.build());

            response = CarResponseFinal.newBuilder()
                    .setStatusCode(CarConstant.STATUS_CODE_SUCCESS)
                    .setStatusMessage(CarConstant.STATUS_MESSAGE_SUCCESS)
                    .setData(carResponse);
        } else {
            response = CarResponseFinal.newBuilder()
                    .setStatusCode(CarConstant.STATUS_CODE_NOT_FOUND)
                    .setStatusMessage(CarConstant.STATUS_MESSAGE_FAILED.replace(CarConstant.Response.VALUE_REPLACE,
                                    "Data with ID ".concat(String.valueOf(request.getId())).concat(" not found!")));
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
