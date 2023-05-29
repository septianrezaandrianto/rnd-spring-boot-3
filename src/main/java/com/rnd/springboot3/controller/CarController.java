package com.rnd.springboot3.controller;

import com.rnd.springboot3.entity.Car;
import com.rnd.springboot3.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@RestController
@RequestMapping("/api")
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping(value = "/saveCars" /*, produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} */)
    public ResponseEntity<CompletableFuture<List<Car>>> saveCars(@RequestParam(value = "files")MultipartFile[] files) {
        try {
            for(final MultipartFile file: files) {
                carService.saveCars(file);
            }
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(final Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/getAllCar" /*, produces = {MediaType.APPLICATION_JSON_VALUE},
        consumes = {MediaType.APPLICATION_JSON_VALUE} */)
    public CompletableFuture<ResponseEntity> getAllCar() {
        return carService.getAllCar().<ResponseEntity>thenApply(ResponseEntity::ok)
                .exceptionally(handleGetCarFailure);
    }

    private Function<Throwable, ResponseEntity<? extends List<Car>>> handleGetCarFailure
            = throwable -> {
        log.error("Failed to read records: {}", throwable);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    };

}
