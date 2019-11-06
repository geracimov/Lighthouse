package ru.geracimov.otus.spring.lighthouse.componentserver.controller;

import com.pi4j.io.gpio.PinDirection;
import com.pi4j.io.gpio.PinMode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.PinModeService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class PinModeController {
    private final PinModeService pinModeService;

    @GetMapping("/pinMode")
    public Collection<PinMode> allPinModes(
            @RequestParam(value = "pinDirection", required = false) PinDirection direction) {
        return pinModeService.allModes(direction);
    }

    @GetMapping("/pinDirection/{pinDirection}/pinMode")
    public Collection<PinMode> allPinModesByDirection(@PathVariable("pinDirection") PinDirection pinDirection) {
        return pinModeService.allModes(pinDirection);
    }

}
