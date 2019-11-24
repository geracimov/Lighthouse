package ru.geracimov.otus.spring.lighthouse.managementserver.controller.dto;

import lombok.Data;
import ru.geracimov.otus.spring.lighthouse.managementserver.domain.State;

import java.util.Map;

@Data
public class ComponentDto {
    private String name;
    private String tag;
    private Map<String, String> properties;
    private State state;
    private boolean closed;
    private boolean open;
    private boolean on;
    private boolean off;

}
