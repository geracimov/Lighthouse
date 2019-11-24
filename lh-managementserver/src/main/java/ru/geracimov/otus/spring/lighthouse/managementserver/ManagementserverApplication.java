package ru.geracimov.otus.spring.lighthouse.managementserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ManagementserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementserverApplication.class, args);
    }

}
