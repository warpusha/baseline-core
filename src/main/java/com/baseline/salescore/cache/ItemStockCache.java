package com.baseline.salescore.cache;

import com.baseline.salescore.entity.InventoryHeader;
import com.baseline.salescore.exception.InsufficientItemStockException;
import com.baseline.salescore.projection.ItemStockProjection;
import com.baseline.salescore.repository.InventoryDtlRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ItemStockCache {

    private InventoryDtlRepository inventoryDtlRepository;
    private Map<Long, Integer> itemStockCache;

    @PostConstruct
    public synchronized void fillCache() {
        log.info("Updating stock cache");
        List<ItemStockProjection> stocks = inventoryDtlRepository.getStocks();
        itemStockCache = new ConcurrentHashMap<>(stocks.stream().collect(Collectors.toMap(ItemStockProjection::getId, ItemStockProjection::getStock)));
        log.info("Item stock cache is up to date, total items {}", itemStockCache.size());
    }

    public void updateCache(InventoryHeader header) {
        header.getInventoryDetails().forEach(d -> {
            Long itemId = d.getItem().getId();
            Integer quantity = d.getQuantity();
            itemStockCache.compute(itemId, (id, stock) -> {
                Integer existingStock = itemStockCache.get(id);
                return existingStock != null ? existingStock + quantity : quantity;
            });
        });
    }

    public void sell(List<InventoryHeader> headers) {
        headers.forEach(header -> {
            Map<Long, Integer> cart = header.getInventoryDetails().stream()
                    .collect(Collectors.toMap(d -> d.getItem().getId(), d -> Math.abs(d.getQuantity())));
            cart.forEach((itemId, quantity) -> itemStockCache.compute(itemId, (k, v) -> {
                Integer stock = itemStockCache.get(k);
                if (stock >= quantity) {
                    return stock - quantity;
                } else {
                    throw new InsufficientItemStockException(itemId, stock);
                }
            }));
        });
    }

    public Map<Long, Integer> getItemStockCache() {
        return itemStockCache;
    }

    @Autowired
    public void setInventoryDtlRepository(InventoryDtlRepository inventoryDtlRepository) {
        this.inventoryDtlRepository = inventoryDtlRepository;
    }
}
