package ru.geracimov.otus.spring.lighthouse.componentserver.domain.exception;

public class InvalidComponentModeException extends RuntimeException {

    public InvalidComponentModeException(String name) {
        super(name);
    }

}
