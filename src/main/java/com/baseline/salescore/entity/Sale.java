package com.baseline.salescore.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "SALE")
@Data
@SequenceGenerator(name = "SEQ_SALE_ID", sequenceName = "SEQ_SALE_ID", allocationSize = 1)
@NoArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALE_ID")
    private Long id;
    private Instant date;
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private Set<SaleItem> saleItems;
    private String customerId;
    @PrePersist
    public void setDate() {
        date = Instant.now();
    }

    public Sale(String customerId) {
        this.customerId = customerId;
    }
}
