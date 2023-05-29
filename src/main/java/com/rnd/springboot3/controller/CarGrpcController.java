package com.rnd.springboot3.controller;

import com.rnd.springboot3.dto.*;
import com.rnd.springboot3.service.CarGrpcService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Map;

@RestController
@RequestMapping("/grpc")
@Slf4j
public class CarGrpcController {

    @Autowired
    private CarGrpcService carGrpcService;

    @GetMapping(value = "/getCar")
    public ResponseEntity<CarResponseFinalDto> getCar(@RequestParam("id") String id) {
        return ResponseEntity.ok(carGrpcService.getCar(id));
    }

    @GetMapping(value = "/getCarList")
    public ResponseEntity<CarResponseListDto> getCarList() {
        return ResponseEntity.ok(carGrpcService.getCarList());
    }

    @PostMapping("/getCarWithPage")
    public ResponseEntity<CarResponseWithPageDto> getCarWithPage(@RequestBody CarRequestDto carRequestDto) {
        return ResponseEntity.ok(carGrpcService.getCarWithPage(carRequestDto));
    }

    @PostMapping("/createCar")
    public ResponseEntity<CarResponseFinalDto> createCar(@RequestBody CarCreateRequestDto carCreateRequestDto) {
        return ResponseEntity.ok(carGrpcService.createCar(carCreateRequestDto));
    }

    @DeleteMapping(value = "/deleteCar")
    public ResponseEntity<CarResponseFinalDto> deleteCar(@RequestParam("id") String id) {
        return ResponseEntity.ok(carGrpcService.deleteCar(id));
    }

    @PutMapping("/updateCar")
    public ResponseEntity<CarResponseFinalDto> updateCar(@RequestBody CarRequestUpdateDto carRequestUpdateDto) {
        return ResponseEntity.ok(carGrpcService.updateCar(carRequestUpdateDto));
    }


    @GetMapping("/test")
    public ResponseEntity<String> test() {
        String result = null;
        CarRequestDto carRequestDto = new CarRequestDto();
        int i =1;
        for(Field f : carRequestDto.getClass().getDeclaredFields()) {
           result = f.getName();
           log.error("RESULT " + i + " = " + result);
           i++;
        }
        return ResponseEntity.ok(result);
    }
}
