package com.baseline.salescore.cache;

import com.baseline.salescore.entity.InventoryHeader;
import com.baseline.salescore.projection.ItemStockProjection;
import com.baseline.salescore.repository.InventoryDtlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class StockCache {

    private InventoryDtlRepository inventoryDtlRepository;
    private ConcurrentHashMap<Long, Integer> stockCache;

    @PostConstruct
    public void fillCache() {
        log.info("Updating stock cache");
        stockCache = new ConcurrentHashMap<>();
        List<ItemStockProjection> stocks = inventoryDtlRepository.getStocks();
        stockCache.putAll(stocks.stream().collect(Collectors.toMap(ItemStockProjection::getId, ItemStockProjection::getStock)));
        log.info("Stock cache is up to date, total items {}", stockCache.size());
    }

    public void updateCache(InventoryHeader header) {
        header.getInventoryDetails().forEach(d -> {
            Long itemId = d.getItem().getId();
            Integer quantity = d.getQuantity();
            stockCache.compute(itemId, (id, stock) -> {
                Integer existingStock = stockCache.get(id);
                return existingStock != null ? existingStock + quantity : quantity;
            });
        });
    }

    public void sellItem(Long itemId) {
        stockCache.compute(itemId, (k, v) -> {
            Integer value = stockCache.get(k);
            return --value;
        });
    }

    public ConcurrentHashMap<Long, Integer> getStockCache() {
        return stockCache;
    }

    @Autowired
    public void setInventoryDtlRepository(InventoryDtlRepository inventoryDtlRepository) {
        this.inventoryDtlRepository = inventoryDtlRepository;
    }
}
