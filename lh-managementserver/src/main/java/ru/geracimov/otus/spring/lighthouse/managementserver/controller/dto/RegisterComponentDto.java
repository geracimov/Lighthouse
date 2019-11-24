package ru.geracimov.otus.spring.lighthouse.managementserver.controller.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import ru.geracimov.otus.spring.lighthouse.managementserver.domain.PinMode;

@Value
@RequiredArgsConstructor
public class RegisterComponentDto {
    private final Integer address;
    private final String name;
    private final PinMode pinMode;
    private final String componentType;

}
