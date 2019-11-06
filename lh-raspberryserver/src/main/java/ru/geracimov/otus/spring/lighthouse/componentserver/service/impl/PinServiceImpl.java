package ru.geracimov.otus.spring.lighthouse.componentserver.service.impl;


import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.gpio.exception.UnsupportedPinModeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.geracimov.otus.spring.lighthouse.componentserver.aspect.LoggingAspectAnnotation;
import ru.geracimov.otus.spring.lighthouse.componentserver.exception.IncorrectPinException;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.PinService;

import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressWarnings("AccessStaticViaInstance")
@LoggingAspectAnnotation
public class PinServiceImpl implements PinService {
    private final GpioProvider gpioProvider;
    private final GpioController gpioController;
    private final PinProvider pinProvider;

    @Override
    public Collection<GpioPin> reservedPins() {
        return gpioController.getProvisionedPins();
    }

    @Override
    public Pin pin(Integer address) {
        return pinProvider.getPinByAddress(address);
    }

    @Override
    public GpioPin reservedPin(Integer address) {
        return gpioController.getProvisionedPin(pin(address));
    }

    @Override
    public GpioPin reservedPin(String name) {
        return gpioController.getProvisionedPin(name);
    }

    @Override
    public List<Pin> allPins() {
        return Arrays.asList(pinProvider.allPins());
    }

    @Override
    public List<Pin> allPins(PinMode pinMode) {
        return pinMode == null
                ? allPins()
                : Arrays.asList(pinProvider.allPins(pinMode));
    }

    @Override
    public GpioPin reservePin(int address, String pinName, PinMode pinMode) {
        Pin pin = pinProvider.getPinByAddress(address);
        if (pin == null) throw new IncorrectPinException(address);
        log.debug("Pin (" + pin + ") register...");

        final GpioPin provisionedPin = gpioController.getProvisionedPin(pin);
        if (provisionedPin != null) {
            provisionedPin.export(pinMode);
            return provisionedPin;
        }

        boolean isModeSupported = pinProvider.getPinByAddress(address)
                                             .getSupportedPinModes()
                                             .contains(pinMode);
        if (!isModeSupported) throw new UnsupportedPinModeException(pin, pinMode);

        final GpioPin gpioPin = gpioController.provisionPin(gpioProvider, pin, pinName, pinMode, PinState.LOW);
        gpioPin.setMode(pinMode);
        log.debug("Pin (" + pin + ") registered as (" + gpioPin + ")");
        gpioPin.addListener((GpioPinListenerDigital) event -> System.out.println(event.getState() + "--------------------------" + event.getEdge()));
        return gpioPin;
    }

    @Override
    public void freePin(Pin pin) {
        log.debug("Pin (" + pin + ") unexported...");
        gpioController.unexport(pin);
    }

    @Override
    public void freePin(int address) {
        Pin pin = pinProvider.getPinByAddress(address);
        freePin(pin);
    }

    @Override
    public void freePins() {
        reservedPins().forEach(gpioPin -> freePin(gpioPin.getPin()));
    }

    @PreDestroy
    private void destroy() {
        log.info("Controller shutdown...");
        gpioController.shutdown();
    }

}
