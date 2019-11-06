package ru.geracimov.otus.spring.lighthouse.componentserver.configuration;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.PinProvider;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.impl.GpioControllerImpl;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GpioConfig {

    @Bean
    public Platform platform() {
        return PlatformManager.getPlatform();
    }

    @Bean
    public GpioProvider gpioProvider(Platform platform) {
        return platform.getGpioProvider();
    }

    @Bean
    public PinProvider pinProvider() {
        return new RaspiPin();
    }

    @Bean
    public GpioController gpioController(GpioProvider gpioProvider) {
        return new GpioControllerImpl(gpioProvider);
    }

}
