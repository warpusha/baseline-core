package com.baseline.sales.service;

import com.baseline.sales.cache.ItemStockCache;
import com.baseline.sales.cache.SkuStockCache;
import com.baseline.sales.constant.Direction;
import com.baseline.sales.constant.HeaderType;
import com.baseline.sales.converter.DtoMapper;
import com.baseline.sales.converter.InventoryConverter;
import com.baseline.sales.dto.InventoryHeaderDto;
import com.baseline.sales.dto.InventoryTransitionDto;
import com.baseline.sales.entity.InventoryHeader;
import com.baseline.sales.entity.Item;
import com.baseline.sales.exception.InvalidRequestException;
import com.baseline.sales.exception.ItemNotFoundException;
import com.baseline.sales.repository.InventoryHdrRepository;
import com.baseline.sales.repository.ItemRepository;
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
