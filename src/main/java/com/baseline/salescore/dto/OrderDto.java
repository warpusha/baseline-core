package com.baseline.salescore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
    Map<Long, Integer> cart;
    String customerId;
}
