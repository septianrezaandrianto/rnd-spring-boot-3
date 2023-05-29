//package com.rnd.springboot3.configuration;
//
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Configuration
//@Component
//public class RedisConfiguration {
//
//    @Bean
//    public RedissonClient redisson() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://localhost:6379"); // Replace with your Redis server details
//        return Redisson.create(config);
//    }
//}
