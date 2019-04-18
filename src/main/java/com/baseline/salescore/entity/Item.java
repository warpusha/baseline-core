package com.baseline.salescore.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ITEM")
@SequenceGenerator(name = "SEQ_ITEM_ID", sequenceName = "SEQ_ITEM_ID", allocationSize = 1)
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ITEM_ID")
    private Long id;
    private String description;
    private BigDecimal unitCost;

}
