package com.rnd.springboot3.service;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class RedisService {

    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://localhost:6379"); // Replace with your Redis server details
        return Redisson.create(config);
    }

    public Map<String, Object> storeDataToRedis(String key) {
        Map<String, Object> result = new HashMap<>();

        RMap<String, Object> rMap = redisson().getMap(key);
        if(!rMap.isExists()) {
            Map<String, Object> mapData = new HashMap<>();
            mapData.put("merk", "Honda");
            mapData.put("type", "Mobilio");
            rMap.put(key, mapData);
            rMap.expire(Duration.ofMinutes(5));

            result.put("data" , mapData);
            result.put("responseStatus", 200);
            result.put("responseMessage", "Success store data to Redis");
        } else {
            result.put("data" , rMap.get(key));
            result.put("responseStatus", 200);
            result.put("responseMessage", "Success get data to Redis");
        }
        return result;
    }




}
