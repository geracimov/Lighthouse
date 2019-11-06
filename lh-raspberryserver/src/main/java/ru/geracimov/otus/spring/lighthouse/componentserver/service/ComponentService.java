package ru.geracimov.otus.spring.lighthouse.componentserver.service;

import com.pi4j.component.Component;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.impl.GpioPinImpl;

import java.util.Collection;

public interface ComponentService {

    <C extends Component> C registerComponent(Class<C> type, int address, String name, PinMode pinMode);

    void unregisterComponent(String name);

    <C extends Component> Collection<C> registeredComponents();

    <C extends Component> C registeredComponent(Integer address);

    <C extends Component> C registeredComponent(String name);

    <C extends Component> GpioPinImpl pinBy(C component);

}
