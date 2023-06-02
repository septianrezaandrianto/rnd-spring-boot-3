package com.rnd.springboot3.service;

import com.rnd.springboot3.entity.Car;
import com.rnd.springboot3.repository.CarRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RedisServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private RedisService redisService;

    @Test
    @DisplayName("should return success storeDataToRedis")
    @Disabled
    public void storeDataToRedis() {
        when(carRepository.getCarByModel(anyString())).thenReturn(car());
        Map<String, Object> result = redisService.storeDataToRedis(anyString());
        assertNotNull(result);
    }

    private Car car() {
        return Car.builder()
                .model("model 1")
                .manufactur("manufactur 1")
                .type("type 1")
                .createdDate(new Date())
                .build();
    }

}
