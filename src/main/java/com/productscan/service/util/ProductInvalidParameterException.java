package com.productscan.service.util;

public class ProductInvalidParameterException extends Exception{
    public ProductInvalidParameterException() {
    }

    public ProductInvalidParameterException(String message) {
        super(message);
    }

    public ProductInvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
