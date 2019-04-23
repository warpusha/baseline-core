package com.baseline.salescore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompositionDto {
    private Long id;
    private ItemDto item;
    private Integer quantity;
}
