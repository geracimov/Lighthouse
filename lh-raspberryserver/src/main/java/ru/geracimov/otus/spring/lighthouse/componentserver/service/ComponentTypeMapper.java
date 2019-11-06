package ru.geracimov.otus.spring.lighthouse.componentserver.service;

import com.pi4j.component.Component;

public interface ComponentTypeMapper {

    Class<? extends Component> getClass(String componentType);

}
