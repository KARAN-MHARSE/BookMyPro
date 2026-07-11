package com.bookmypro.customer_service.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    CUSTOMER_ALREADY_EXISTS(
            "CUS_001",
            HttpStatus.CONFLICT,
            "Customer already exists."
    ),

    CUSTOMER_NOT_FOUND(
            "CUS_002",
            HttpStatus.NOT_FOUND,
            "Customer not found."
    ),

    IDENTITY_SERVICE_UNAVAILABLE(
            "CUS_003",
            HttpStatus.SERVICE_UNAVAILABLE,
            "Identity service is unavailable."
    ),

    INVALID_IDENTITY_RESPONSE(
            "CUS_004",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Received an invalid response from Identity Service."
    ),

    CUSTOMER_CREATION_FAILED(
            "CUS_005",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Failed to create customer."
    ),

    CUSTOMER_PROFILE_NOT_FOUND(
            "CUS_006",
            HttpStatus.NOT_FOUND,
            "Customer profile not found."
    ),

    CUSTOMER_ALREADY_VERIFIED(
            "CUS_007",
            HttpStatus.CONFLICT,
            "Customer is already verified."
    ),

    INVALID_CUSTOMER_STATUS(
            "CUS_008",
            HttpStatus.BAD_REQUEST,
            "Customer is in an invalid state for this operation."
    ),

    SOMETHING_WENT_WRONG(
            "CUS_999",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Something went wrong."
    );

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}