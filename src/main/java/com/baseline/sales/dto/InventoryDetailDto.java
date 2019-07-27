package com.baseline.sales.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryDetailDto {
    private Long id;
    private ItemDto item;
    private Integer quantity;
    private String direction;
}
