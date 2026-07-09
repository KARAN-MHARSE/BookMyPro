package com.bookmypro.identity_service.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ProblemDetail handlBusinessException(BusinessException ex) {

		ErrorCode errorCode = ex.getErrorCode();

		ProblemDetail problemDetail = ProblemDetail.forStatus(errorCode.getHttpStatus());

		problemDetail.setTitle("Business Exception");
		problemDetail.setDetail(errorCode.getMessage());

		problemDetail.setProperty("code", errorCode.getCode());

		return problemDetail;
	}
	
	 @ExceptionHandler(Exception.class)
	    public ProblemDetail handleGenericException(Exception ex) {

	        ProblemDetail problemDetail =
	                ProblemDetail.forStatus(500);

	        problemDetail.setTitle("Internal Server Error");
	        problemDetail.setDetail("Something went wrong. Please try again later.");

	        return problemDetail;
	    }
}
