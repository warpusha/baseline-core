package com.baseline.sales.repository;

import com.baseline.sales.entity.InventoryHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryHdrRepository extends JpaRepository<InventoryHeader, Long> {
}
