package com.bookmypro.provider_service.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {

		ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

		problem.setTitle("Validation Failed");
		problem.setDetail("Invalid Data.");
		problem.setInstance(java.net.URI.create(request.getRequestURI()));

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		problem.setProperty("errors", errors);

		return problem;
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleGenericException(Exception ex) {

		ProblemDetail problemDetail = ProblemDetail.forStatus(500);

		problemDetail.setTitle("Internal Server Error");
		problemDetail.setDetail("Something went wrong. Please try again later.");

		return problemDetail;
	}

}
