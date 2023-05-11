package com.rnd.springboot3.grpc;

import com.google.gson.Gson;
import com.rnd.springboot3.*;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(CarGrpc.class);

    private static SimpleDateFormat DATE_DISPLAY = new SimpleDateFormat("dd/MM/yyyy");

    private CarDao carDao = new CarDao();
    /**
     * Unary operation to get the car based on id
     * @param request
     * @param responseObserver
     */

    @Override
    public void getCar(CarRequest request, StreamObserver<CarResponse> responseObserver) {

        Car car = carDao.getCarById(request.getId());

        CarResponse carResponse = CarResponse.newBuilder()
                .setId(request.getId())
                .setManufactur(car.getManufactur())
                .setModel(car.getModel())
                .setType(car.getType())
                .setCreatedDate(DATE_DISPLAY.format(car.getCreatedDate()))
                .build();

        //set the response object
        responseObserver.onNext(carResponse);
        //mark process is completed
        responseObserver.onCompleted();
    }

    @Override
    public void getCarList(CarRequest request, StreamObserver<CarResponseList> responseObserver) {
        List<Car> carList = carDao.getCarList();

        CarResponseList.Builder carBuilder = CarResponseList.newBuilder();

        for(Car car : carList) {
            carBuilder.addCarResponse(CarResponse.newBuilder()
                    .setId(car.getId())
                    .setManufactur(car.getManufactur())
                    .setModel(car.getModel())
                    .setType(car.getType())
                    .setCreatedDate(DATE_DISPLAY.format(car.getCreatedDate()))
                .build()
            );
        }
        CarResponseList result = carBuilder.build();

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getCarWithPage(CarRequestWithPage request, StreamObserver<CarResponseWithPage> responseObserver) {
        Page<Car> carPage = carDao.getCarListWithPage(request.getRowPerPage(), request.getRowNumber(), request.getKey());
        CarResponseWithPage.Builder carBuilder = CarResponseWithPage.newBuilder();

        for(Car car : carPage.getContent()) {
            carBuilder.addCarResponse(CarResponse.newBuilder()
                    .setId(car.getId())
                    .setManufactur(car.getManufactur())
                    .setModel(car.getModel())
                    .setType(car.getType())
                    .setCreatedDate(DATE_DISPLAY.format(car.getCreatedDate()))
                    .build()
            );
        }

        CarResponseWithPage result = carBuilder
                .setRowNumber(request.getRowNumber())
                .setRowPerPage(request.getRowPerPage())
                .setTotalData((int) carPage.getTotalElements())
                .build();

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void getCarWithManualPage(CarRequestWithPage request, StreamObserver<CarResponseWithPage> responseObserver) {
        CarResponseDto carResponseDto = carDao.getCarListWithManualPage(request.getRowPerPage(),
                request.getRowNumber(), request.getKey());

        CarResponseWithPage.Builder carBuilder = CarResponseWithPage.newBuilder();

        for(Car car : carResponseDto.getCarList()) {
            carBuilder.addCarResponse(CarResponse.newBuilder()
                    .setId(car.getId())
                    .setManufactur(car.getManufactur())
                    .setModel(car.getModel())
                    .setType(car.getType())
                    .setCreatedDate(DATE_DISPLAY.format(car.getCreatedDate()))
                    .build()
            );
        }

        CarResponseWithPage result = carBuilder
                .setRowNumber(carResponseDto.getRowNumber())
                .setRowPerPage(carResponseDto.getRowPerpage())
                .setTotalData(carResponseDto.getTotalData())
                .build();

        responseObserver.onNext(result);
        responseObserver.onCompleted();
    }

    @Override
    public void createCar(CarCreateRequest request, StreamObserver<CarCreateResponse> responseObserver) {
        Car car = Car.builder()
            .manufactur(request.getManufactur())
            .model(request.getModel())
            .type(request.getType())
            .createdDate(new Date())
            .build();

        carDao.insertCar(car);

        CarCreateResponse response = CarCreateResponse.newBuilder()
                .setManufactur(car.getManufactur())
                .setModel(car.getModel())
                .setType(car.getType())
                .setCreatedDate(DATE_DISPLAY.format(car.getCreatedDate()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
    
    @Override
    public void deleteCar(CarRequest request, StreamObserver<CarDeleteResponse> responseObserver) {
        carDao.deleteCar(request.getId());

        CarDeleteResponse response = CarDeleteResponse.newBuilder()
                .setMessage("Success delete data with ID ".concat(String.valueOf(request.getId())))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateCar(CarUpdateRequest request, StreamObserver<CarResponse> responseObserver) {
        Car car = carDao.getCarById(request.getId());

        CarResponse response = null;
        if(car != null) {
            response = CarResponse.newBuilder()
                    .setId(request.getId())
                    .setManufactur(request.getManufactur())
                    .setType(request.getType())
                    .setModel(request.getModel())
                    .setCreatedDate(DATE_DISPLAY.format(car.getCreatedDate()))
                    .build();
            carDao.updateCar(response);
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
