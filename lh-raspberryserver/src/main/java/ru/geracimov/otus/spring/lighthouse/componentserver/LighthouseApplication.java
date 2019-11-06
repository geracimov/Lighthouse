package ru.geracimov.otus.spring.lighthouse.componentserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class LighthouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(LighthouseApplication.class, args);
    }

}
