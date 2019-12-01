package ru.geracimov.otus.spring.lighthouse.componentserver.domain;

import java.util.Map;

public interface Component {

    ComponentType getType();

    void setName(String name);

    String getName();

    void setProperty(String key, String value);

    boolean hasProperty(String key);

    String getProperty(String key, String defaultValue);

    String getProperty(String key);

    Map<String, String> getProperties();

    void removeProperty(String key);

}
