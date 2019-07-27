package com.baseline.sales.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryTransitionDto {
    @JsonProperty(required = true)
    private Long itemId;
    private String type;
    private String description;
    private Integer quantity;
    private String direction;
}
