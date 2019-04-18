package com.baseline.salescore.entity;

import com.baseline.salescore.constants.Direction;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "INVENTORY_DETAIL")
@Data
@SequenceGenerator(name = "SEQ_INVENTORY_DETAIL_ID", sequenceName = "SEQ_INVENTORY_DETAIL_ID", allocationSize = 1)
public class InventoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INVENTORY_DETAIL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "INVENTORY_HDR_ID")
    private InventoryHeader inventoryHeader;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Direction direction;
}
