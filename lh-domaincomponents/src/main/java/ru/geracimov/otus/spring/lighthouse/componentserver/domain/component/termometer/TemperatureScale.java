package ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.termometer;

import lombok.Getter;

@Getter
public enum TemperatureScale {

    CELSIUS("°C"),
    FARENHEIT("°F");

    private final String unit;

    TemperatureScale(String unit) {
        this.unit = unit;
    }

}
