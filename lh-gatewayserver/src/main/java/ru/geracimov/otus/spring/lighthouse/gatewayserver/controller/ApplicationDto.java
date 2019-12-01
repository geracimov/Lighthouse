package ru.geracimov.otus.spring.lighthouse.gatewayserver.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class ApplicationDto implements Serializable {
    private final String name;
    private final Integer instanceCount;

    @JsonCreator
    public ApplicationDto(@NonNull String name, Integer instanceCount) {
        this.name = name.toLowerCase();
        this.instanceCount = instanceCount;
    }

    public ApplicationDto(@NonNull InstanceInfo instanceInfo) {
        this.name = instanceInfo.getAppName().toLowerCase();
        this.instanceCount = 1;
    }

    public String getName() {
        return name;
    }

    public Integer getInstanceCount() {
        return instanceCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationDto that = (ApplicationDto) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
