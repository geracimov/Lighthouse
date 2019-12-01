package ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.relay;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.ComponentBase;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.ComponentType;

public final class RelayComponent extends ComponentBase implements Relay {

    private final static PinState DEFAULT_STATE = PinState.HIGH;

    public RelayComponent(GpioPinImpl pin) {
        super(pin, ComponentType.RELAY);
        this.pin.setState(DEFAULT_STATE);
    }

    @Override
    public void open() {
        this.pin.low();
    }

    @Override
    public void close() {
        this.pin.high();
    }

    @Override
    public boolean isOpen() {
        return this.pin.isLow();
    }
}
