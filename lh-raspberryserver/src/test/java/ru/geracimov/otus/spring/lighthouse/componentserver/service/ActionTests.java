package ru.geracimov.otus.spring.lighthouse.componentserver.service;

import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.relay.RelayComponent;
import ru.geracimov.otus.spring.lighthouse.componentserver.repository.ComponentRepository;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.impl.PinServiceImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.geracimov.otus.spring.lighthouse.componentserver.helper.JavaHelper.currentMethodName;
import static ru.geracimov.otus.spring.lighthouse.componentserver.helper.StringHelper.getHeader;

@SpringBootTest(classes = {ServiceConfigurationTests.class, PinServiceImpl.class})
class ActionTests {

    @MockBean
    ComponentRepository componentRepository;

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
        GpioPin pin = pinService.reservePin(pinNum, pinName, PinMode.DIGITAL_OUTPUT, PinState.LOW);
        GpioPinImpl gpioPin = (GpioPinImpl) pin;
        RelayComponent relay = new RelayComponent(gpioPin);

        relay.close();
        assertFalse(relay.isOpen());
        relay.open();
        assertTrue(relay.isOpen());
    }

}
