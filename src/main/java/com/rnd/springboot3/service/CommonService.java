package com.rnd.springboot3.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class CommonService {

    @Value("${spring.data.redis.port}")
    private long RedisPort;

    @Value("${spring.data.redis.host}")
    private String redisHost;

    protected RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(redisHost + ":" + RedisPort);
        return Redisson.create(config);
    }

}
