package ru.geracimov.otus.spring.lighthouse.componentserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class InvalidComponentNameException extends RuntimeException {

    public InvalidComponentNameException(String componentName) {
        super("Invalid component name [" + componentName + "]");
    }

}
