package com.rnd.springboot3.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

@Slf4j
public class CommonService {

    protected RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
//                // local
//                .setAddress("redis://localhost:6379"); // Replace with your Redis server details
        return Redisson.create(config);
    }

}
