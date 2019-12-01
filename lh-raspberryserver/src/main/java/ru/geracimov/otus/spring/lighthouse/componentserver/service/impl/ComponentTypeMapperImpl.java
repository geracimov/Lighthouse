package ru.geracimov.otus.spring.lighthouse.componentserver.service.impl;

import ru.geracimov.otus.spring.lighthouse.componentserver.domain.Component;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.led.LEDComponent;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.relay.RelayComponent;
import ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.termometer.Dht11Component;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.geracimov.otus.spring.lighthouse.componentserver.exception.InvalidComponentTypeException;
import ru.geracimov.otus.spring.lighthouse.componentserver.service.ComponentTypeMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ComponentTypeMapperImpl implements ComponentTypeMapper {
    private static final Map<String, Class<? extends Component>> components;

    static {
        components = new HashMap<>();
        components.put("relay", RelayComponent.class);
        components.put("led", LEDComponent.class);
        components.put("dht", Dht11Component.class);
    }

    @Override
    public Class<? extends Component> getClass(String componentType) {
        val opt = Optional.ofNullable(components.get(componentType));
        return opt.orElseThrow(() -> new InvalidComponentTypeException(componentType));
    }

}
