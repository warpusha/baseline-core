package com.baseline.salescore.repository;

import com.baseline.salescore.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
