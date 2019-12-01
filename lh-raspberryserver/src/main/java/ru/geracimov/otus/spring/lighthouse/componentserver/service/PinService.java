package ru.geracimov.otus.spring.lighthouse.componentserver.service;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;

import java.util.Collection;
import java.util.List;

public interface PinService {

    Pin pin(Integer address);

    GpioPin reservedPin(Integer address);

    GpioPin reservedPin(String name);

    List<Pin> allPins();

    List<Pin> allPins(PinMode pinMode);

    Collection<GpioPin> reservedPins();

    GpioPin reservePin(int address, String pinName, PinMode pinMode, PinState pinState);

    void freePin(Pin pin);

    void freePin(int address);

    void freePins();

}
