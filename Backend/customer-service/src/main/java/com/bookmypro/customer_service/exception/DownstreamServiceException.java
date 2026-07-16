package com.bookmypro.customer_service.exception;

import org.springframework.http.HttpStatus;

public class DownstreamServiceException extends RuntimeException {
    private final HttpStatus status;
    private final String code;

    public DownstreamServiceException(HttpStatus status, String message, String code) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
