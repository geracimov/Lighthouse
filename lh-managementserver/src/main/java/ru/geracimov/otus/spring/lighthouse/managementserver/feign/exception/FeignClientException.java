package ru.geracimov.otus.spring.lighthouse.managementserver.feign.exception;

import feign.FeignException;

public class FeignClientException extends FeignException {


    public FeignClientException(int status, String reason) {
        super(status, reason);
    }

}
