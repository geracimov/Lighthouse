package ru.geracimov.otus.spring.lighthouse.componentserver.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import ru.geracimov.otus.spring.lighthouse.componentserver.controller.dto.RegisterComponentDto;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.Component;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.termometer.Dht11Component;
import ru.geracimov.otus.spring.lighthouse.componentserver.exception.InvalidComponentNameException;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ComponentService;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ComponentTypeMapper;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ComponentController {
    private final ComponentService componentService;
    private final ComponentTypeMapper componentNameMapper;

    @GetMapping("/component")
    public <C extends Component> Collection<C> registeredComponents() {
        return componentService.registeredComponents();
    }

    @GetMapping("/component/{name}")
    public <C extends Component> C registeredComponent(@PathVariable String name) {
        return componentService.registeredComponent(name);
    }

    @SuppressWarnings("unchecked")
    @PostMapping("/component/register")
    public <C extends Component> C registerComponent(@RequestBody RegisterComponentDto componentDto) {
        val componentClass = componentNameMapper.getClass(componentDto.getComponentType());
        return (C) componentService.registerComponent(componentClass,
                                                      componentDto.getAddress(),
                                                      componentDto.getName(),
                                                      componentDto.getPinMode());
    }

    @GetMapping("/component/{name}/temperature")
    @HystrixCommand(commandKey = "getComponentData", fallbackMethod = "registeredComponent", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    }
    )
    public <C extends Component>  C   getComponentData(@PathVariable String name) {
        Optional<Component> componentOptional = Optional.ofNullable(componentService.registeredComponent(name));
        Component component = componentOptional.orElseThrow(() -> new InvalidComponentNameException(name));

        final Dht11Component dht11Component = (Dht11Component) component;
        dht11Component.calcTemperature();
        return (C) dht11Component;
    }

    @PutMapping("/component/{name}/toggle")
    public Component toggle(@PathVariable String name) {
        Optional<Component> componentOptional = Optional.ofNullable(componentService.registeredComponent(name));
        Component component = componentOptional.orElseThrow(() -> new InvalidComponentNameException(name));

        final GpioPinImpl gpioPin = componentService.pinBy(component);
        gpioPin.toggle();
        return component;
    }

    @PutMapping("/component/{name}/on")
    public Component on(@PathVariable String name) {
        Optional<Component> componentOptional = Optional.ofNullable(componentService.registeredComponent(name));
        Component component = componentOptional.orElseThrow(() -> new InvalidComponentNameException(name));

        final GpioPinImpl gpioPin = componentService.pinBy(component);
        gpioPin.setState(true);
        return component;
    }

    @PutMapping("/component/{name}/off")
    public Component off(@PathVariable String name) {
        Optional<Component> componentOptional = Optional.ofNullable(componentService.registeredComponent(name));
        Component component = componentOptional.orElseThrow(() -> new InvalidComponentNameException(name));

        final GpioPinImpl gpioPin = componentService.pinBy(component);
        gpioPin.setState(false);
        return component;
    }

    @PutMapping("/component/{name}/blink/{delay}/{duration}")
    public Component blink(@PathVariable String name, @PathVariable Integer delay, @PathVariable Integer duration) {
        Optional<Component> componentOptional = Optional.ofNullable(componentService.registeredComponent(name));
        Component component = componentOptional.orElseThrow(() -> new InvalidComponentNameException(name));

        final GpioPinImpl gpioPin = componentService.pinBy(component);
        gpioPin.blink(delay, duration);
        return component;
    }

    @PutMapping("/component/{name}/pulse/{delay}")
    public Component pulse(@PathVariable String name, @PathVariable Integer delay) {
        Optional<Component> componentOptional = Optional.ofNullable(componentService.registeredComponent(name));
        Component component = componentOptional.orElseThrow(() -> new InvalidComponentNameException(name));

        final GpioPinImpl gpioPin = componentService.pinBy(component);
        gpioPin.pulse(delay);
        return component;
    }

    @PutMapping("/component/{name}/unregister")
    public void unregisterComponent(@PathVariable String name) {
        componentService.unregisterComponent(name);
    }

    @PutMapping("/component/unregister")
    public void unregisterAllComponents() {
        componentService.unregisterAllComponents();
    }

}
