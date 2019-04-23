package com.baseline.salescore.cache;

import com.baseline.salescore.entity.Composition;
import com.baseline.salescore.entity.Sale;
import com.baseline.salescore.entity.SaleItem;
import com.baseline.salescore.entity.Sku;
import com.baseline.salescore.exception.InsufficientSkuStockException;
import com.baseline.salescore.exception.SkuNotFoundException;
import com.baseline.salescore.repository.SkuRepository;
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
public class SkuStockCache {

    private SkuRepository skuRepository;
    private ItemStockCache itemStockCache;
    private Map<Long, Integer> skuStockCache;

    @PostConstruct
    public synchronized void fillCache() {
        log.info("Updating SKU stock cache");
        Map<Long, Integer> cache = new ConcurrentHashMap<>();
        List<Sku> skuList = skuRepository.findAll();
        Map<Long, Map<Long, Integer>> skuItems = skuList.stream()
                .collect(Collectors.toMap(Sku::getId,
                        s -> s.getCompositions().stream()
                                .collect(Collectors.toMap(c -> c.getItem().getId(), Composition::getQuantity))));
        Map<Long, Integer> itemStockCache = this.itemStockCache.getItemStockCache();
        skuItems.forEach((skuId, compositions) -> {
            Integer possibleSkuCount = null;
            for (Map.Entry<Long, Integer> entry : compositions.entrySet()) {
                Integer itemsInStock = itemStockCache.get(entry.getKey());
                Integer compositionCount = itemsInStock / entry.getValue();
                if (possibleSkuCount == null) {
                    possibleSkuCount = compositionCount;
                } else if (possibleSkuCount > compositionCount) {
                    possibleSkuCount = compositionCount;
                }
            }
            cache.put(skuId, possibleSkuCount);
        });
        skuStockCache = cache;
        log.info("SKU stock cache is up to date, total SKUs {}", skuStockCache.size());
    }

    public void checkStock(Map<Long, Integer> cart) {
        cart.forEach((skuId, quantity) -> {
                    Integer stock = skuStockCache.get(skuId);
                    if (stock == null) {
                        throw new SkuNotFoundException(skuId);
                    } else if (stock < quantity) {
                        throw new InsufficientSkuStockException(skuId, stock);
                    }
                }
        );
    }

    public void sell(Sale sale) {
        Map<Long, Integer> cart = sale.getSaleItems().stream().collect(Collectors.toMap(s -> s.getSku().getId(), SaleItem::getQuantity));
        cart.forEach((itemId, quantity) -> skuStockCache.compute(itemId, (k, v) -> {
            Integer stock = skuStockCache.get(k);
            if (stock >= quantity) {
                return stock - quantity;
            } else {
                throw new InsufficientSkuStockException(itemId, stock);
            }
        }));
    }

    public Map<Long, Integer> getSkuStockCache() {
        return skuStockCache;
    }

    @Autowired
    public void setSkuRepository(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    @Autowired
    public void setItemStockCache(ItemStockCache itemStockCache) {
        this.itemStockCache = itemStockCache;
    }
}
