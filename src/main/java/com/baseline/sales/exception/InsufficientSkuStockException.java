package com.baseline.sales.exception;

public class InsufficientSkuStockException extends RuntimeException {
    public InsufficientSkuStockException(Long itemId, Integer stock) {
        super(String.format("Insufficient stock of SKU with id %d, current stock %d", itemId, stock));
    }
}
