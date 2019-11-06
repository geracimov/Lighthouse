package ru.geracimov.otus.spring.lighthouse.componentserver.service.impl;

import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.PinModeService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class PinModeServiceImpl implements PinModeService {

    @Override
    public Collection<PinMode> allModes() {
        return PinMode.all();
    }

    @Override
    public Collection<PinMode> allModes(PinDirection pinDirection) {
        if (pinDirection == null) return allModes();
        return PinDirection.IN.equals(pinDirection) ? PinMode.allInputs() : PinMode.allOutput();
    }


}
