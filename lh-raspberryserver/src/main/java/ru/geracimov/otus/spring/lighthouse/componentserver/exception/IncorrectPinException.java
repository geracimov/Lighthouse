package ru.geracimov.otus.spring.lighthouse.componentserver.exception;

public class IncorrectPinException extends RuntimeException {
    private final int address;

    public IncorrectPinException(int address) {
        super("Pin address " + address + " is incorrect");
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

}
