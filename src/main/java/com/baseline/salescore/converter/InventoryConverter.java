package com.baseline.salescore.converter;

import com.baseline.salescore.constant.Direction;
import com.baseline.salescore.constant.HeaderType;
import com.baseline.salescore.dto.InventoryTransitionDto;
import com.baseline.salescore.entity.InventoryDetail;
import com.baseline.salescore.entity.InventoryHeader;
import com.baseline.salescore.entity.Item;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;

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
        header.setInventoryDetails(Collections.singleton(transitionToDetail(transition, header, item)));
        return header;

    }
}
