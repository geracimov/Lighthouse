package ru.geracimov.otus.spring.lighthouse.componentserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableEurekaClient
@EnableAspectJAutoProxy
@SpringBootApplication
public class LighthouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LighthouseApplication.class, args);
    }

}
