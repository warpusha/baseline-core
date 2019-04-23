package com.baseline.salescore.entity;


import com.baseline.salescore.constant.HeaderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "INVENTORY_HEADER")
@Data
@SequenceGenerator(name = "SEQ_INVENTORY_HEADER_ID", sequenceName = "SEQ_INVENTORY_HEADER_ID", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_INVENTORY_HEADER_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    private HeaderType type;

    private Instant date;

    private String description;

    @OneToMany(mappedBy = "inventoryHeader", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<InventoryDetail> inventoryDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SALE_ITEM_ID", insertable = false, updatable = false)
    private SaleItem saleItem;

    @PrePersist
    public void setDate() {
        date = Instant.now();
    }
}
