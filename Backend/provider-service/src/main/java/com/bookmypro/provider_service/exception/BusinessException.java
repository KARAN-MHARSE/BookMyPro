package com.bookmypro.provider_service.exception;

import lombok.RequiredArgsConstructor;

public class BusinessException extends RuntimeException{	
    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
    public ErrorCode getErrorCode() {
    	return this.errorCode;
    }
}
