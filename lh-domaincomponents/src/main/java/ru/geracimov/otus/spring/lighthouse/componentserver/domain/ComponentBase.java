package ru.geracimov.otus.spring.lighthouse.componentserver.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.impl.GpioPinImpl;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ComponentBase implements Serializable, Component {
    @Getter
    @JsonIgnore
    protected final GpioPinImpl pin;
    @Getter
    protected final ComponentType type;

    @Getter @Setter
    private String name;
    @Getter
    private final Map<String, String> properties = new ConcurrentHashMap<>();

    @Override
    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        if (hasProperty(key)) {
            this.properties.get(key);
        }
        return defaultValue;
    }

    @Override
    public String getProperty(String key) {
        return getProperty(key, "");
    }

    @Override
    public void removeProperty(String key) {
        properties.remove(key);
    }

}
