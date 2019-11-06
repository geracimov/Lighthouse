package ru.geracimov.otus.spring.lighthouse.componentserver.exception;

public class InvalidComponentNameException extends RuntimeException {

    public InvalidComponentNameException(String componentName) {
        super("Invalid component name [" + componentName + "]");
    }

}
