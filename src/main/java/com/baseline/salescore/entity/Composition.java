package com.baseline.salescore.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@SequenceGenerator(name = "SEQ_COMPOSITION_ID", sequenceName = "SEQ_COMPOSITION_ID", allocationSize = 1)
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMPOSITION_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "SKU_ID", insertable = false, updatable = false)
    private Sku sku;
}
