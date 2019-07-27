package com.baseline.sales.repository;

import com.baseline.sales.entity.InventoryDetail;
import com.baseline.sales.projection.ItemStockProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryDtlRepository extends JpaRepository<InventoryDetail, Long> {

    @Query(value = "select i.id as id, sum(d.quantity) as stock from item i " +
            "join inventory_detail d on i.id = d.item_id " +
            "group by i.id order by stock desc", nativeQuery = true)
    List<ItemStockProjection> getStocks();
}
