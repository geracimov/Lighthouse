package ru.geracimov.otus.spring.lighthouse.componentserver.exception;

public class RegisterComponentException extends RuntimeException {
    private final Class<?> clazz;

    public RegisterComponentException(Class<?> clazz, Exception e) {
        super("Class " + clazz.getSimpleName() + " is incorrect", e);
        this.clazz = clazz;
    }

    public Class<?> getInstanceClass() {
        return clazz;
    }

}
