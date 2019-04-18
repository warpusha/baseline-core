package com.baseline.salescore.repository;

import com.baseline.salescore.entity.InventoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryDtlRepository extends JpaRepository<InventoryDetail, Long> {
}
