package com.baseline.salescore.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "SALE")
@Data
@SequenceGenerator(name = "SEQ_SALE_ID", sequenceName = "SEQ_SALE_ID", allocationSize = 1)
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALE_ID")
    private Long id;
    private Instant date;
    @OneToMany(mappedBy = "sale")
    private List<SaleItem> saleItems;

    @PrePersist
    public void setDate() {
        date = Instant.now();
    }
}
