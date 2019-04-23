package com.baseline.salescore.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkuDto {
    private Long id;
    private String name;
    private String description;
    private Double retailPrice;
    private Set<CompositionDto> compositions;

    public Set<CompositionDto> getCompositions() {
        if (compositions == null) {
            compositions = new HashSet<>();
        }
        return compositions;
    }
}
