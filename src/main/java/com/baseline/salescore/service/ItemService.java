package com.baseline.salescore.service;

import com.baseline.salescore.converters.DtoMapper;
import com.baseline.salescore.dto.ItemDto;
import com.baseline.salescore.entity.Item;
import com.baseline.salescore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private DtoMapper mapper;
    private ItemRepository itemRepository;

    public Item createItem(ItemDto input) {
        Item item = mapper.itemDtoToItem(input);
        return itemRepository.save(item);
    }

    @Autowired
    public void setMapper(DtoMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
}
