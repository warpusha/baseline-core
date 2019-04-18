package com.baseline.salescore.converters;

import com.baseline.salescore.dto.ItemDto;
import com.baseline.salescore.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    Item itemDtoToItem(ItemDto src);
}
