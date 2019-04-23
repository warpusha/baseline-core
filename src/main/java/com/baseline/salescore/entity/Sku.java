package com.baseline.salescore.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "SKU")
@SequenceGenerator(name = "SEQ_SKU_ID", sequenceName = "SEQ_SKU_ID", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SKU_ID")
    private Long id;
    private String name;
    private String description;
    private BigDecimal retailPrice;
    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @BatchSize(size = 20)
    private Set<Composition> compositions;

    public Set<Composition> getCompositions() {
        if (compositions == null) {
            compositions = new HashSet<>();
        }
        return compositions;
    }
}
