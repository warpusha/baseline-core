package com.baseline.salescore.converter;

import com.baseline.salescore.dto.OrderDto;
import com.baseline.salescore.entity.Sale;
import com.baseline.salescore.entity.SaleItem;
import com.baseline.salescore.entity.Sku;
import com.baseline.salescore.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SaleConverter {

    private SkuRepository skuRepository;

    public Sale orderToSale(OrderDto order) {
        Sale sale = new Sale(order.getCustomerId());
        sale.setSaleItems(orderToSaleItems(order, sale));
        return sale;
    }

    private Set<SaleItem> orderToSaleItems(OrderDto order, Sale sale) {
        Map<Long, Integer> cart = order.getCart();
        List<Sku> skuInCart = skuRepository.findAllById(cart.keySet());
        return skuInCart.stream().map(sku ->
                SaleItem.builder().quantity(cart.get(sku.getId())).sku(sku)
                        .salePrice(sku.getRetailPrice()).sale(sale).build()
        ).collect(Collectors.toSet());
    }

    @Autowired
    public void setSkuRepository(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }
}
