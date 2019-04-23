package com.baseline.salescore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleDto {
    private Long id;
    private LocalDateTime date;
    private String customerId;
    private List<SaleItemDto> saleItems;

}
