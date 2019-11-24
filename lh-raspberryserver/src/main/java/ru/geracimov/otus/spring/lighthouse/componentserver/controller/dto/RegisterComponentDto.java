package ru.geracimov.otus.spring.lighthouse.componentserver.controller.dto;

import com.pi4j.io.gpio.PinMode;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class RegisterComponentDto {
    private final Integer address;
    private final String name;
    private final PinMode pinMode;
    private final String componentType;

}
