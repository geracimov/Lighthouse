package ru.geracimov.otus.spring.lighthouse.componentserver.controller;

import com.pi4j.component.Component;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.*;
import ru.geracimov.otus.spring.lighthouse.componentserver.controller.dto.RegisterComponentDto;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ComponentService;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ComponentTypeMapper;

import java.util.Collection;

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
        val componentClass = componentNameMapper.getClass(componentDto.getComponentName());
        return (C) componentService.registerComponent(componentClass,
                                                      componentDto.getAddress(),
                                                      componentDto.getName(),
                                                      componentDto.getPinMode());
    }

    @PutMapping("/component/{name}/toggle")
    public void toggle(@PathVariable String name) {
        Component component = componentService.registeredComponent(name);
        final GpioPinImpl gpioPin = componentService.pinBy(component);
        gpioPin.toggle();
    }

    @PutMapping("/component/{name}/unregister")
    public void unregisterComponent(@PathVariable String name) {
        componentService.unregisterComponent(name);
    }

}
