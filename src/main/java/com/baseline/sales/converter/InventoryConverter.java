package com.baseline.sales.converter;

import com.baseline.sales.constant.Direction;
import com.baseline.sales.constant.HeaderType;
import com.baseline.sales.dto.InventoryTransitionDto;
import com.baseline.sales.entity.*;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InventoryConverter {

    private InventoryDetail transitionToDetail(InventoryTransitionDto transition, InventoryHeader header, Item item) {
        return InventoryDetail.builder().item(item).inventoryHeader(header).quantity(transition.getQuantity())
                .direction(EnumUtils.getEnumIgnoreCase(Direction.class, transition.getDirection())).build();
    }

    public InventoryHeader transitionToHeader(InventoryTransitionDto transition, Item item) {
        InventoryHeader header = InventoryHeader.builder().description(transition.getDescription())
                .type(EnumUtils.getEnumIgnoreCase(HeaderType.class, transition.getType()))
                .build();
        header.setInventoryDetails(Collections.singletonList(transitionToDetail(transition, header, item)));
        return header;

    }

    public List<InventoryHeader> saleToHeaders(Sale sale) {
        return sale.getSaleItems().stream().map(saleItem -> {
            InventoryHeader header = InventoryHeader.builder().type(HeaderType.SALE)
                    .description(String.format("Sale %d", sale.getId())).saleItem(saleItem).build();
            header.setInventoryDetails(saleItemToDetails(saleItem, header));
            return header;
        }).collect(Collectors.toList());
    }

    private List<InventoryDetail> saleItemToDetails(SaleItem saleItem, InventoryHeader header) {
        return saleItem.getSku().getCompositions().stream().map(c ->
                InventoryDetail.builder().direction(Direction.OUT).quantity(Math.negateExact(c.getQuantity() * saleItem.getQuantity()))
                        .inventoryHeader(header).item(c.getItem()).build()).collect(Collectors.toList());
    }
}
