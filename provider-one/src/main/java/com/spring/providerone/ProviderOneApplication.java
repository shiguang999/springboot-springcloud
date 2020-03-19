package com.spring.providerone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.spring.providerone.dao")
public class ProviderOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderOneApplication.class, args);
    }
}
