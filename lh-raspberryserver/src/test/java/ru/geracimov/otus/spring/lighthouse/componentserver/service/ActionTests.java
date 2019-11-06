package ru.geracimov.otus.spring.lighthouse.componentserver.service;

import com.pi4j.component.relay.Relay;
import com.pi4j.component.relay.RelayState;
import com.pi4j.component.relay.impl.GpioRelayComponent;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.impl.PinServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.geracimov.otus.spring.lighthouse.componentserver.helper.JavaHelper.currentMethodName;
import static ru.geracimov.otus.spring.lighthouse.componentserver.helper.StringHelper.getHeader;

@SpringBootTest(classes = {ServiceConfigurationTests.class, PinServiceImpl.class})
class ActionTests {

    @Autowired
    private PinService pinService;

    @BeforeAll
    static void setProperties() {
        System.setProperty("pi4j.platform", "simulated");
    }

    @Test
    void togglePinTest() {
        System.out.println(getHeader(currentMethodName()));
        int pinNum = 25;
        String pinName = "relay";
        GpioPin pin = pinService.reservePin(pinNum, pinName, PinMode.DIGITAL_OUTPUT);
        GpioPinDigitalOutput gpioPin = (GpioPinDigitalOutput) pin;
        Relay relay = new GpioRelayComponent(gpioPin);

        assertEquals(RelayState.OPEN, relay.getState());
        relay.toggle();
        assertEquals(RelayState.CLOSED, relay.getState());
        relay.toggle();
        assertEquals(RelayState.OPEN, relay.getState());
    }

}
