package com.rnd.springboot3.controller;

import com.rnd.springboot3.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/save")
    public ResponseEntity<Map<String, Object>>  save(@RequestParam("model") String model) {
        return ResponseEntity.ok(redisService.storeDataToRedis(model));
    }
}
