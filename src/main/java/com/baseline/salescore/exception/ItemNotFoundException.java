package com.baseline.salescore.exception;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super(String.format("Item with id %d is not found in database", id));
    }
}
