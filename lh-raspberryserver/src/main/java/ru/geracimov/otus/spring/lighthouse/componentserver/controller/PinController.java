package ru.geracimov.otus.spring.lighthouse.componentserver.controller;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.PinService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PinController {
    private final PinService pinService;

    @GetMapping("/pin")
    public List<Pin> allPins(@RequestParam(required = false) PinMode pinMode) {
        return pinService.allPins(pinMode);
    }

    @GetMapping("/pinMode/{pinMode}/pin")
    public List<Pin> allPinModesByDirection(@PathVariable("pinMode") PinMode pinMode) {
        return pinService.allPins(pinMode);
    }

    @PostMapping("/pin/{address}/free")
    public void freePin(@PathVariable("address") Integer address) {
        pinService.freePin(address);
    }

}
