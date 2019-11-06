package ru.geracimov.otus.spring.lighthouse.componentserver.service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.PinProvider;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.impl.GpioControllerImpl;
import com.pi4j.platform.Platform;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.impl.PinServiceImpl;

@Configuration
@SpringBootConfiguration
@Import({PinServiceImpl.class})
@ComponentScan({"ru.geracimov.otus.spring.lighthouse.componentserver.service"})
public class ServiceConfigurationTests {

    @Bean
    public Platform platform() {
        return Platform.SIMULATED;
    }

    @Bean
    public GpioProvider gpioProvider(Platform platform) {
        return platform.getGpioProvider();
    }

    @Bean
    public GpioController gpioController(GpioProvider gpioProvider) {
        return new GpioControllerImpl(gpioProvider);
    }

    @Bean
    public PinProvider pinProvider() {
        return new RaspiPin();
    }

}
