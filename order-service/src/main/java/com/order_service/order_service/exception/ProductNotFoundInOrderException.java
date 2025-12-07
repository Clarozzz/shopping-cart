package com.order_service.order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotFoundInOrderException extends RuntimeException {

    public ProductNotFoundInOrderException(String message) {
        super(message);
    }

}
