package ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.termometer;

public interface Thermometer {

    double getTemperature();

    void calcTemperature();

    TemperatureScale getScale();

}
