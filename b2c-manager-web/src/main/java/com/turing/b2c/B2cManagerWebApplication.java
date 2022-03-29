package com.turing.b2c;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class B2cManagerWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(B2cManagerWebApplication.class, args);
    }

}
