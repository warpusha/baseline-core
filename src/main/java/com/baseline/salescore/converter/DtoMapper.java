package com.baseline.salescore.converter;

import com.baseline.salescore.dto.*;
import com.baseline.salescore.entity.*;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    SaleDto saleToDto(Sale sale, @Context TimeZone timeZone);

    SaleItemDto saleItemToDto(SaleItem saleItem);

    SkuDto skuToDto(Sku sku);

    List<SkuDto> skusToDto(List<Sku> sku);

    CompositionDto compositionToDto(Composition composition);

    Set<CompositionDto> compositionToDto(Set<Composition> composition);

    Item itemDtoToItem(ItemDto src);

    @Mapping(target = "stock", ignore = true)
    ItemDto itemToDto(Item item);

    List<ItemDto> itemsToDto(List<Item> item);

    InventoryHeaderDto headerToDto(InventoryHeader header, @Context TimeZone timeZone);

    InventoryDetailDto detailToDto(InventoryDetail detail);

    default LocalDateTime fromInstant(Instant instant, @Context TimeZone timeZone) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, timeZone.toZoneId());
    }
}
