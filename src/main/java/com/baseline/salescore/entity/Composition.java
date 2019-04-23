package com.baseline.salescore.entity;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
@SequenceGenerator(name = "SEQ_COMPOSITION_ID", sequenceName = "SEQ_COMPOSITION_ID", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "sku")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMPOSITION_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ITEM_ID", updatable = false)
    private Item item;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "SKU_ID", updatable = false)
    private Sku sku;
}
