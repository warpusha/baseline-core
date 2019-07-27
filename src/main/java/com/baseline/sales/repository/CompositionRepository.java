package com.baseline.sales.repository;

import com.baseline.sales.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompositionRepository extends JpaRepository<Item, Long> {
}
