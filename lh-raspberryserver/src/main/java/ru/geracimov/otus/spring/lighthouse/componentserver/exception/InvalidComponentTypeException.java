package ru.geracimov.otus.spring.lighthouse.componentserver.exception;

public class InvalidComponentTypeException extends RuntimeException {

    public InvalidComponentTypeException(String componentType) {
        super("Invalid component type name [" + componentType + "]");
    }

}
