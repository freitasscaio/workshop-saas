package com.example.saas.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationException(
	        MethodArgumentNotValidException exception,
	        HttpServletRequest request) {

	    Map<String, String> fieldErrors = exception.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .collect(Collectors.toMap(
	                    FieldError::getField,
	                    FieldError::getDefaultMessage,
	                    (existing, replacement) -> existing,
	                    LinkedHashMap::new
	            ));

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            HttpStatus.BAD_REQUEST.getReasonPhrase(),
	            "Existem campos inválidos.",
	            request.getRequestURI(),
	            fieldErrors
	    );

	    return ResponseEntity
	            .badRequest()
	            .body(response);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
	        ResourceNotFoundException exception,
	        HttpServletRequest request) {

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.NOT_FOUND.value(),
	            HttpStatus.NOT_FOUND.getReasonPhrase(),
	            exception.getMessage(),
	            request.getRequestURI(),
	            null
	    );

	    return ResponseEntity
	            .status(HttpStatus.NOT_FOUND)
	            .body(response);
	}
	
	@ExceptionHandler(BusinessRuleException.class)
	public ResponseEntity<ErrorResponse> handleBusinessRuleException(
	        BusinessRuleException exception,
	        HttpServletRequest request) {

	    ErrorResponse response = new ErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            HttpStatus.BAD_REQUEST.getReasonPhrase(),
	            exception.getMessage(),
	            request.getRequestURI(),
	            null
	    );

	    return ResponseEntity
	            .badRequest()
	            .body(response);
	}

}
