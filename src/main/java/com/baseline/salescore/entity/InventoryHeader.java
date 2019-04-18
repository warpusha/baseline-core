package com.baseline.salescore.entity;


import com.baseline.salescore.constants.HeaderType;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "INVENTORY_HEADER")
@Data
@SequenceGenerator(name = "SEQ_INVENTORY_HEADER_ID", sequenceName = "SEQ_INVENTORY_HEADER_ID", allocationSize = 1)
public class InventoryHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INVENTORY_HEADER_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private HeaderType type;

    private Instant date;

    private String description;

    @ManyToOne
    @JoinColumn(name = "SALE_ITEM_ID", insertable = false, updatable = false)
    private SaleItem saleItem;

}
