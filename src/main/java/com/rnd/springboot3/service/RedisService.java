package com.rnd.springboot3.service;

import com.rnd.springboot3.constant.CarConstant;
import com.rnd.springboot3.entity.Car;
import com.rnd.springboot3.exception.NotFoundException;
import com.rnd.springboot3.repository.CarRepository;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class RedisService extends CommonService {

    @Value("${spring.cache.redis.time-to-live}")
    private long ttl;

    @Autowired
    private CarRepository carRepository;

    public Map<String, Object> storeDataToRedis(String model) {
        Map<String, Object> result = new HashMap<>();
        Car car = carRepository.getCarByModel(model);

        if(car == null) {
              throw new NotFoundException(CarConstant.STATUS_CODE_NOT_FOUND, CarConstant.STATUS_MESSAGE_FAILED
                      .replace("{value}", "Data not found!"));
        }

        RMap<String, Object> rMap = redisson().getMap(model);
        if(!rMap.isExists()) {
            rMap.put(model, car);
            rMap.expire(Duration.ofMinutes(ttl));

            result.put("data" , car);
            result.put("responseStatus", CarConstant.STATUS_CODE_SUCCESS);
            result.put("responseMessage", "Success store data to Redis");
        } else {
            result.put("data" , rMap.get(model));
            result.put("responseStatus", CarConstant.STATUS_CODE_SUCCESS);
            result.put("responseMessage", "Success get data to Redis");
        }
        return result;
    }




}
