package com.baseline.sales.repository;

import com.baseline.sales.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, Long> {

}
