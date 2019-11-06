package ru.geracimov.otus.spring.lighthouse.componentserver.controller;

import com.pi4j.io.gpio.PinDirection;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.PinModeService;

import java.util.Collection;
import java.util.EnumSet;

@RestController
@RequiredArgsConstructor
public class PinDirectionController {
    private final PinModeService pinModeService;

    @GetMapping("/pinDirection")
    public Collection<PinDirection> allPinModes() {
        return EnumSet.allOf(PinDirection.class);
    }

}
