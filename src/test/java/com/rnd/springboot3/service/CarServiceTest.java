package com.rnd.springboot3.service;

import com.rnd.springboot3.entity.Car;
import com.rnd.springboot3.repository.CarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {

    @Mock
    private Logger LOGGER;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    @DisplayName("should return success saveCars service")
    public void saveCars() throws Exception {
        String csvContent = "BMW;X5;SUV\n" +
                "Toyota;Camry;Sedan\n" +
                "Honda;CR-V;SUV\n";
        MockMultipartFile file = new MockMultipartFile("cars.csv", csvContent.getBytes());

        when(carRepository.saveAll(anyList())).thenReturn(carList());
        CompletableFuture<List<Car>> future = carService.saveCars(file);
        verify(carRepository, times(1)).saveAll(anyList());
        List<Car> savedCars = future.get();
        assertEquals(3, savedCars.size());
        assertEquals("BMW", savedCars.get(0).getManufactur());
        assertEquals("X5", savedCars.get(0).getModel());
        assertEquals("SUV", savedCars.get(0).getType());

        assertEquals("Toyota", savedCars.get(1).getManufactur());
        assertEquals("Camry", savedCars.get(1).getModel());
        assertEquals("Sedan", savedCars.get(1).getType());

        assertEquals("Honda", savedCars.get(2).getManufactur());
        assertEquals("CR-V", savedCars.get(2).getModel());
        assertEquals("SUV", savedCars.get(2).getType());
    }


    @Test
    @DisplayName("sould return success getAllCar service")
    public void getAllCar() throws ExecutionException, InterruptedException {
        when(carRepository.findAll()).thenReturn(carList());
        CompletableFuture<List<Car>> cars = carService.getAllCar();
        verify(carRepository, times(1)).findAll();
        List<Car> savedCars = cars.get();
        assertEquals(3, savedCars.size());
        assertEquals("BMW", savedCars.get(0).getManufactur());
        assertEquals("X5", savedCars.get(0).getModel());
        assertEquals("SUV", savedCars.get(0).getType());

        assertEquals("Toyota", savedCars.get(1).getManufactur());
        assertEquals("Camry", savedCars.get(1).getModel());
        assertEquals("Sedan", savedCars.get(1).getType());

        assertEquals("Honda", savedCars.get(2).getManufactur());
        assertEquals("CR-V", savedCars.get(2).getModel());
        assertEquals("SUV", savedCars.get(2).getType());
    }

    private List<Car> carList() {
        return Arrays.asList(
                Car.builder().manufactur("BMW").model("X5").type("SUV").createdDate(new Date()).build(),
                Car.builder().manufactur("Toyota").model("Camry").type("Sedan").createdDate(new Date()).build(),
                Car.builder().manufactur("Honda").model("CR-V").type("SUV").createdDate(new Date()).build()
        );
    }

}
