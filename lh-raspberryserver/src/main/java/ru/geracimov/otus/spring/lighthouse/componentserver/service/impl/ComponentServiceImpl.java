package ru.geracimov.otus.spring.lighthouse.componentserver.service.impl;

import com.pi4j.component.Component;
import com.pi4j.io.gpio.GpioPin;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geracimov.otus.spring.lighthouse.componentserver.aspect.LoggingAspectAnnotation;
import ru.geracimov.otus.spring.lighthouse.componentserver.exception.InvalidComponentNameException;
import ru.geracimov.otus.spring.lighthouse.componentserver.exception.InvalidComponentTypeException;
import ru.geracimov.otus.spring.lighthouse.componentserver.exception.RegisterComponentException;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ComponentService;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.PinService;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@LoggingAspectAnnotation
public class ComponentServiceImpl implements ComponentService {
    private static final Class<?> PARENT_CLASS = GpioPin.class;
    private final PinService pinService;
    private final Map<GpioPinImpl, Component> components = new HashMap<>();

    @Override
    public <C extends Component> C registerComponent(Class<C> componentClass, int address, String name,
                                                     PinMode pinMode) {
        try {
            Class<?> needGpioPinClass = PARENT_CLASS;
            for (Constructor<?> constructor : componentClass.getDeclaredConstructors()) {
                if (constructor.getParameterCount() == 1 &&
                        PARENT_CLASS.isAssignableFrom(constructor.getParameterTypes()[0])) {
                    needGpioPinClass = constructor.getParameterTypes()[0];
                }
            }
            GpioPin gpioPin = pinService.reservePin(address, name, pinMode);
            C component = componentClass.getDeclaredConstructor(needGpioPinClass)
                                        .newInstance(needGpioPinClass.cast(gpioPin));
            component.setName(name);
            component.setProperty("class", componentClass.getName());

            components.put((GpioPinImpl) gpioPin, component);
            return component;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RegisterComponentException(componentClass, e);
        }
    }

    @Override
    public void unregisterComponent(String name) {
        Component component = registeredComponent(name);
        if (component == null) throw new InvalidComponentNameException(name);
        final GpioPin gpioPin = pinBy(component);
        Pin pin = gpioPin.getPin();
        pinService.freePin(pin);
        components.remove(gpioPin);
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "unchecked"})
    @Override
    public <C extends Component> C registeredComponent(Integer address) {
        GpioPin gpioPin = pinService.reservedPin(address);
        return (C) components.get(gpioPin);
    }

    @SuppressWarnings({"SuspiciousMethodCalls", "unchecked"})
    @Override
    public <C extends Component> C registeredComponent(String name) {
        GpioPin gpioPin = pinService.reservedPin(name);
        return (C) components.get(gpioPin);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Component> registeredComponents() {
        return components.values();
    }

    @Override
    public GpioPinImpl pinBy(@NotNull Component component) {
        for (Map.Entry<GpioPinImpl, Component> pair : components.entrySet()) {
            if (pair.getValue()
                    .equals(component)) return pair.getKey();
        }
        throw new InvalidComponentTypeException(component.getName());
    }

}
