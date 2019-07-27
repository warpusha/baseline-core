package com.baseline.sales.service;

import com.baseline.sales.cache.ItemStockCache;
import com.baseline.sales.cache.SkuStockCache;
import com.baseline.sales.converter.DtoMapper;
import com.baseline.sales.converter.InventoryConverter;
import com.baseline.sales.converter.SaleConverter;
import com.baseline.sales.dto.OrderDto;
import com.baseline.sales.dto.SaleDto;
import com.baseline.sales.entity.Sale;
import com.baseline.sales.exception.InvalidRequestException;
import com.baseline.sales.repository.InventoryHdrRepository;
import com.baseline.sales.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Map;

@Service
public class SaleService {

    private SkuStockCache skuStockCache;
    private ItemStockCache itemStockCache;
    private SaleConverter saleConverter;
    private SaleRepository saleRepository;
    private InventoryConverter inventoryConverter;
    private InventoryHdrRepository hdrRepository;
    private DtoMapper dtoMapper;
    private Calendar now = Calendar.getInstance();

    @Transactional
    public SaleDto sell(OrderDto order) {
        validateOrder(order);
        Map<Long, Integer> cart = order.getCart();
        skuStockCache.checkStock(cart);
        Sale sale = saleRepository.save(saleConverter.orderToSale(order));
        skuStockCache.sell(sale);
        itemStockCache.sell(hdrRepository.saveAll(inventoryConverter.saleToHeaders(sale)));
        return dtoMapper.saleToDto(sale, now.getTimeZone());
    }

    private void validateOrder(OrderDto order) {
        Map<Long, Integer> cart = order.getCart();
        if (cart.values().stream().anyMatch(i -> i <= 0)) {
            throw new InvalidRequestException("Invalid SKU count", order);
        }
    }

    @Autowired
    public void setSkuStockCache(SkuStockCache skuStockCache) {
        this.skuStockCache = skuStockCache;
    }

    @Autowired
    public void setSaleConverter(SaleConverter saleConverter) {
        this.saleConverter = saleConverter;
    }

    @Autowired
    public void setSaleRepository(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Autowired
    public void setInventoryConverter(InventoryConverter inventoryConverter) {
        this.inventoryConverter = inventoryConverter;
    }

    @Autowired
    public void setHdrRepository(InventoryHdrRepository hdrRepository) {
        this.hdrRepository = hdrRepository;
    }

    @Autowired
    public void setDtoMapper(DtoMapper dtoMapper) {
        this.dtoMapper = dtoMapper;
    }

    @Autowired
    public void setItemStockCache(ItemStockCache itemStockCache) {
        this.itemStockCache = itemStockCache;
    }
}
