package ru.geracimov.otus.spring.lighthouse.componentserver.controller.dto;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinMode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@RequiredArgsConstructor
public class GpioPinDto {
    private final Integer address;
    private final String name;
    private final PinMode pinMode;

    public GpioPinDto(@NotNull GpioPin gpioPin) {
        this.address = gpioPin.getPin().getAddress();
        this.name = gpioPin.getName();
        this.pinMode = gpioPin.getMode();
    }

}
