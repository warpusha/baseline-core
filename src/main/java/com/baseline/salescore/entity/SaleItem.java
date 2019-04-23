package com.baseline.salescore.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SALE_ITEM")
@Data
@SequenceGenerator(name = "SEQ_SALE_ITEM_ID", sequenceName = "SEQ_SALE_ITEM_ID", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "sale")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALE_ITEM_ID")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "SKU_ID", updatable = false)
    private Sku sku;
    private BigDecimal salePrice;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "SALE_ID", updatable = false)
    private Sale sale;


}
