package ru.geracimov.otus.spring.lighthouse.componentserver.domain.component.relay;

public interface Relay {

    void open();

    void close();

    boolean isOpen();

}
