package ru.geracimov.otus.spring.lighthouse.componentserver.service;


import ru.geracimov.otus.spring.lighthouse.componentserver.domain.Component;

public interface ComponentTypeMapper {

    Class<? extends Component> getClass(String componentType);

}
