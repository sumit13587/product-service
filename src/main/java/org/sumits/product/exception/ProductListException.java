package org.sumits.product.exception;

public class ProductListException extends RuntimeException {

    private String message;

    public ProductListException(String message) {
        super(message);
    }

    public ProductListException(String message, Throwable err) {
        super(message, err);
    }

}
