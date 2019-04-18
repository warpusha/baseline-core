package com.baseline.salescore.repository;

import com.baseline.salescore.entity.Sku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkuRepository extends JpaRepository<Sku, Long> {

}
