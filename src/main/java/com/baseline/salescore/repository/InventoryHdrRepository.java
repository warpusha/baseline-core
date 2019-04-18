package com.baseline.salescore.repository;

import com.baseline.salescore.entity.InventoryHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryHdrRepository extends JpaRepository<InventoryHeader, Long> {
}
