package com.spring.providertwo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.spring.providertwo.dao")
public class ProviderTwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderTwoApplication.class, args);
    }
}
