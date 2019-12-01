package ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.led;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.ComponentBase;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.ComponentType;

public final class LEDComponent extends ComponentBase implements LED {

    private final static PinState DEFAULT_STATE = PinState.LOW;

    @JsonCreator
    public LEDComponent(GpioPinImpl pin) {
        super(pin, ComponentType.LED);
        this.pin.setState(DEFAULT_STATE);
    }

    @Override
    public void on() {
        this.getPin().high();
    }

    @Override
    public void off() {
        this.getPin().low();
    }

    @Override
    public boolean isOn() {
        return this.getPin().isHigh();
    }

}
