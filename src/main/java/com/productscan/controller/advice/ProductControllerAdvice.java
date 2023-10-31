package com.productscan.controller.advice;

import com.productscan.dto.ErrorMessageResponse;
import com.productscan.service.util.ProductAlreadyExistsException;
import com.productscan.service.util.ProductInvalidParameterException;
import com.productscan.service.util.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.Date;

@RestControllerAdvice
public class ProductControllerAdvice {
    @ExceptionHandler(value = ProductInvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageResponse handleProductInvalidParameter(ProductInvalidParameterException ex, WebRequest request) {
        return ErrorMessageResponse.builder()
                .error_statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .error_message(ex.getMessage())
                .error_description(request.getDescription(false)).build();
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageResponse handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        return ErrorMessageResponse.builder()
                .error_statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .error_message(ex.getMessage())
                .error_description(request.getDescription(false)).build();
    }

    @ExceptionHandler(value = ProductAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessageResponse handleProductAlreadyExistsException(ProductAlreadyExistsException ex, WebRequest request) {
        return ErrorMessageResponse.builder()
                .error_statusCode(HttpStatus.CONFLICT.value())
                .timestamp(new Date())
                .error_message(ex.getMessage())
                .error_description(request.getDescription(false)).build();
    }

    @ExceptionHandler(value = IOException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessageResponse handleFileException(IOException ex, WebRequest request) {
        return ErrorMessageResponse.builder()
                .error_statusCode(HttpStatus.SERVICE_UNAVAILABLE.value())
                .timestamp(new Date())
                .error_message(ex.getMessage())
                .error_description(request.getDescription(false)).build();
    }
}
