package ru.geracimov.otus.spring.lighthouse.componentserver.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.pi4j.component.Component;
import com.pi4j.component.temperature.impl.Dht11Component;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import ru.geracimov.otus.spring.lighthouse.componentserver.controller.dto.RegisterComponentDto;
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

    @SuppressWarnings("unchecked")
    @PostMapping("/component/register")
    public <C extends Component> C registerComponent(@RequestBody RegisterComponentDto componentDto) {
        val componentClass = componentNameMapper.getClass(componentDto.getComponentType());
        return (C) componentService.registerComponent(componentClass,
                                                      componentDto.getAddress(),
                                                      componentDto.getName(),
                                                      componentDto.getPinMode());
    }

    @GetMapping("/component/{name}/data")
    @HystrixCommand(commandKey = "getComponentData", fallbackMethod = "getZero", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "7000")
    }
    )
    public double getComponentData(@PathVariable String name) {
        Optional<Component> componentOptional = Optional.ofNullable(componentService.registeredComponent(name));
        Component component = componentOptional.orElseThrow(() -> new InvalidComponentNameException(name));

        return ((Dht11Component) component).getTemperature();
    }

    public double getZero(String name) {
        return -999;
    }

    @PutMapping("/component/{name}/toggle")
    public void toggle(@PathVariable String name) {
        Optional<Component> componentOptional = Optional.ofNullable(componentService.registeredComponent(name));
        Component component = componentOptional.orElseThrow(() -> new InvalidComponentNameException(name));

        final GpioPinImpl gpioPin = componentService.pinBy(component);
        gpioPin.toggle();
    }

    @PutMapping("/component/{name}/unregister")
    public void unregisterComponent(@PathVariable String name) {
        componentService.unregisterComponent(name);
    }

}
