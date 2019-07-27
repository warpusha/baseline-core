package com.baseline.sales.entity;

import com.baseline.sales.constant.Direction;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "INVENTORY_DETAIL")
@Data
@SequenceGenerator(name = "SEQ_INVENTORY_DETAIL_ID", sequenceName = "SEQ_INVENTORY_DETAIL_ID", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"inventoryHeader"})
public class InventoryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INVENTORY_DETAIL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "INVENTORY_HDR_ID", updatable = false)
    private InventoryHeader inventoryHeader;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ITEM_ID", updatable = false)
    private Item item;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private Direction direction;
}
