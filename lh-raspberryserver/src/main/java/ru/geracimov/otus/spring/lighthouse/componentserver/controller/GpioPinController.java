package ru.geracimov.otus.spring.lighthouse.componentserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geracimov.otus.spring.lighthouse.componentserver.controller.dto.GpioPinDto;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.PinService;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class GpioPinController {
    private final PinService pinService;

    @GetMapping("/gpioPin")
    public Collection<GpioPinDto> registeredGpioPins() {
        return pinService.reservedPins()
                         .stream()
                         .map(GpioPinDto::new)
                         .collect(Collectors.toList());
    }

//    @PostMapping("/gpioPin/register")
//    public GpioPinDto registerComponent(@RequestBody GpioPinDto dto) {
//        final GpioPin gpioPin = pinService.reservePin(dto.getAddress(), dto.getName(), dto.getPinMode());
//        return new GpioPinDto(gpioPin);
//    }

}
