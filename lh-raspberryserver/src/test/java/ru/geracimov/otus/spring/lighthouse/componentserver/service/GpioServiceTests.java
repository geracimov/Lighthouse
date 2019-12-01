package ru.geracimov.otus.spring.lighthouse.componentserver.service;

import com.pi4j.io.gpio.*;
import com.pi4j.platform.PlatformManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geracimov.otus.spring.lighthouse.componentserver.repository.ComponentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.geracimov.otus.spring.lighthouse.componentserver.helper.JavaHelper.currentMethodName;
import static ru.geracimov.otus.spring.lighthouse.componentserver.helper.StringHelper.getHeader;

@SpringBootTest(classes = {ServiceConfigurationTests.class})
class GpioServiceTests {

//    GpioProvider gpioProvider = new SimulatedGpioProvider();

    @MockBean
    ComponentRepository componentRepository;

    @MockBean
    PlatformManager platformManager;

    @Autowired
    private GpioController controller;

    @BeforeEach
    void setUp() {
        System.setProperty("pi4j.platform", "simulated");

    }

    @AfterEach
    void gpioShutdown() {
        System.out.println(getHeader(currentMethodName()));
        controller.shutdown();
    }

    @Test
    void checkProvisionedPinsTest() {
        System.out.println(getHeader(currentMethodName()));

        assertThat(controller.getProvisionedPins()).hasSize(0);
        GpioPin pin28 = controller.provisionDigitalOutputPin(RaspiPin.GPIO_28);

        assertThat(controller.getProvisionedPins()).hasSize(1);
        pin28.unexport();
        GpioPin pin25 = controller.provisionDigitalOutputPin(RaspiPin.GPIO_25);
        assertThat(controller.getProvisionedPins()).hasSize(2);
        if (pin25.isExported()) pin25.unexport();

        for (GpioPin gpioPin : controller.getProvisionedPins()) {
            System.out.println(getHeader(gpioPin.getName(), 20));
            System.out.println(gpioPin.getName());
            System.out.println(gpioPin.getMode());
            System.out.println(gpioPin.getPin());
            System.out.println(gpioPin.getProperties());
            System.out.println(gpioPin.getProvider()
                                      .getName());
            System.out.println(gpioPin.isExported());
            System.out.println(gpioPin.getTag());
            System.out.println(gpioPin);
        }
        controller.shutdown();
    }

    @Test
    void checkAllPinsTest() {
        System.out.println(getHeader(currentMethodName()));

        for (Pin pin : RaspiPin.allPins()) {
            assertTrue(pin.supportsPinEdges());
            assertTrue(pin.supportsPinEvents());
            assertTrue(pin.supportsPinPullResistance());
        }
    }

}
