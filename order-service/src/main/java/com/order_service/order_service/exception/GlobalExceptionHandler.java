package com.order_service.order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> orderNotFoundException(OrderNotFoundException ex, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        int status = httpStatus.value();

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                LocalDateTime.now(),
                status,
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(ProductNotFoundInOrderException.class)
    public ResponseEntity<ErrorResponseDto> productNotFoundInOrderException(ProductNotFoundInOrderException ex, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        int status = httpStatus.value();

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                LocalDateTime.now(),
                status,
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(PaymentProcessingException.class)
    public ResponseEntity<ErrorResponseDto> paymentProcessingException(PaymentProcessingException ex, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        int status = httpStatus.value();

        ErrorResponseDto errorResponse = new ErrorResponseDto(
                LocalDateTime.now(),
                status,
                httpStatus.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest request) {

        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error occurred: " + ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
