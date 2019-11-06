package ru.geracimov.otus.spring.lighthouse.componentserver.service;

import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import org.springframework.lang.Nullable;

import java.util.Collection;

public interface PinModeService {

    Collection<PinMode> allModes();

    Collection<PinMode> allModes(@Nullable PinDirection pinDirection);

}
