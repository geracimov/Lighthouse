package ru.geracimov.otus.spring.lighthouse.componentserver.service.impl;

import com.pi4j.component.relay.RelayState;
import com.pi4j.component.relay.impl.GpioRelayComponent;
import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.impl.GpioControllerImpl;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ComponentService;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.PinService;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ServiceConfigurationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ComponentServiceImpl.class, PinServiceImpl.class, ServiceConfigurationTests.class})
class ComponentServiceImplTest {
    @Autowired
    ComponentService componentService;

    @MockBean
    PinService pinService;
    @MockBean
    GpioProvider gpioProvider;


    @Test
    void registerRelayTest() {
        int address = 25;
        String pinName = "relay";
        RelayState defaultState = RelayState.CLOSED;

        when(pinService.reservePin(address,
                                   pinName,
                                   PinMode.DIGITAL_OUTPUT))
                .thenReturn(new GpioPinImpl(new GpioControllerImpl(
                        gpioProvider), gpioProvider, RaspiPin.GPIO_25));

        GpioRelayComponent relay = componentService.registerComponent(GpioRelayComponent.class,
                                                                      address,
                                                                      pinName,
                                                                      PinMode.DIGITAL_OUTPUT);
        assertThat(relay).isNotNull();
        assertThat(relay.getName()).isEqualTo(pinName);
        assertThat(relay.getState()).isEqualByComparingTo(defaultState);
    }


}