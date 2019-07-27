package com.baseline.sales.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleItemDto {
    private Long id;
    private SkuDto sku;
    private Double salePrice;
    private Integer quantity;
}
