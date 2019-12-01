package ru.geracimov.otus.spring.lighthouse.componentserver.service.impl;

import com.pi4j.io.gpio.GpioProvider;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.impl.GpioControllerImpl;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.relay.RelayComponent;
import ru.geracimov.otus.spring.lighthouse.componentserver.repository.ComponentRepository;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ComponentService;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.PinService;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ServiceConfigurationTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ComponentServiceImpl.class, PinServiceImpl.class, ServiceConfigurationTests.class})
class ComponentServiceImplTest {

    @MockBean
    ComponentRepository componentRepository;

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

        when(pinService.reservePin(address,
                                   pinName,
                                   PinMode.DIGITAL_OUTPUT,
                                   PinState.HIGH))
                .thenReturn(new GpioPinImpl(new GpioControllerImpl(
                        gpioProvider), gpioProvider, RaspiPin.GPIO_25));

        RelayComponent relay = componentService.registerComponent(RelayComponent.class,
                                                                  address,
                                                                  pinName,
                                                                  PinMode.DIGITAL_OUTPUT);
        assertThat(relay).isNotNull();
        assertThat(relay.getName()).isEqualTo(pinName);
        assertFalse(relay.isOpen());
    }


}