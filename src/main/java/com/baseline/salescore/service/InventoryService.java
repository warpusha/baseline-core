package com.baseline.salescore.service;

import com.baseline.salescore.cache.ItemStockCache;
import com.baseline.salescore.cache.SkuStockCache;
import com.baseline.salescore.constant.Direction;
import com.baseline.salescore.constant.HeaderType;
import com.baseline.salescore.converter.DtoMapper;
import com.baseline.salescore.converter.InventoryConverter;
import com.baseline.salescore.dto.InventoryHeaderDto;
import com.baseline.salescore.dto.InventoryTransitionDto;
import com.baseline.salescore.entity.InventoryHeader;
import com.baseline.salescore.entity.Item;
import com.baseline.salescore.exception.InvalidRequestException;
import com.baseline.salescore.exception.ItemNotFoundException;
import com.baseline.salescore.repository.InventoryHdrRepository;
import com.baseline.salescore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Map;

@Service
public class InventoryService {
    private ItemStockCache itemStockCache;
    private SkuStockCache skuStockCache;
    private ItemRepository itemRepository;
    private InventoryHdrRepository inventoryHdrRepository;
    private InventoryConverter inventoryConverter;
    private DtoMapper mapper;
    private Calendar now = Calendar.getInstance();

    @Transactional
    public Map<Long, Integer> getStock() {
        return itemStockCache.getItemStockCache();
    }

    @Transactional
    public InventoryHeaderDto updateStock(InventoryTransitionDto stock) {
        validateTransiton(stock);
        Item item = itemRepository.findById(stock.getItemId()).orElseThrow(() -> new ItemNotFoundException(stock.getItemId()));
        InventoryHeader header = inventoryHdrRepository.save(inventoryConverter.transitionToHeader(stock, item));
        itemStockCache.updateCache(header);
        skuStockCache.fillCache();
        return mapper.headerToDto(header, now.getTimeZone());
    }

    private void validateTransiton(InventoryTransitionDto transition) {
        if (transition.getQuantity() == null || transition.getQuantity() == 0) {
            throw new InvalidRequestException("Transition with zero quantity", transition);
        } else {
            if (HeaderType.PURCHASE.name().equalsIgnoreCase(transition.getType()) && (transition.getQuantity() < 0)) {
                throw new InvalidRequestException("Negative quantity with type PURCHASE", transition);
            } else if ((transition.getQuantity() > 0 && Direction.OUT.name().equalsIgnoreCase(transition.getDirection())) ||
                    (transition.getQuantity() < 0 && Direction.IN.name().equalsIgnoreCase(transition.getDirection()))) {
                throw new InvalidRequestException("Invalid request", transition);
            }
        }

    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setInventoryHdrRepository(InventoryHdrRepository inventoryHdrRepository) {
        this.inventoryHdrRepository = inventoryHdrRepository;
    }

    @Autowired
    public void setInventoryConverter(InventoryConverter inventoryConverter) {
        this.inventoryConverter = inventoryConverter;
    }

    @Autowired
    public void setMapper(DtoMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setItemStockCache(ItemStockCache itemStockCache) {
        this.itemStockCache = itemStockCache;
    }

    @Autowired
    public void setSkuStockCache(SkuStockCache skuStockCache) {
        this.skuStockCache = skuStockCache;
    }
}
