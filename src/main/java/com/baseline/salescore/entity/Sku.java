package com.baseline.salescore.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "SKU")
@SequenceGenerator(name = "SEQ_SKU_ID", sequenceName = "SEQ_SKU_ID", allocationSize = 1)
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SKU_ID")
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "sku", cascade = CascadeType.REMOVE)
    private List<Composition> compositions;
}
