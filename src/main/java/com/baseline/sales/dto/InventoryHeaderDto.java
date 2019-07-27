package com.baseline.sales.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryHeaderDto {
    private Long id;
    private String type;
    private LocalDateTime date;
    private String description;
    private List<InventoryDetailDto> inventoryDetails;

}
