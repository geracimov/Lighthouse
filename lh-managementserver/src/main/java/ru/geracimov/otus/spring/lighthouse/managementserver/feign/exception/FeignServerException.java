package ru.geracimov.otus.spring.lighthouse.managementserver.feign.exception;

import feign.FeignException;

public class FeignServerException extends FeignException {


    public FeignServerException(int status, String reason) {
        super(status, reason);
    }

}
