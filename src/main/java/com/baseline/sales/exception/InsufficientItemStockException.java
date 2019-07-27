package com.baseline.sales.exception;

public class InsufficientItemStockException extends RuntimeException {
    public InsufficientItemStockException(Long itemId, Integer stock) {
        super(String.format("Insufficient stock of item with id %d, current stock %d", itemId, stock));
    }
}
