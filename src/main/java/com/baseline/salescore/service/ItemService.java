package com.baseline.salescore.service;

import com.baseline.salescore.converter.DtoMapper;
import com.baseline.salescore.dto.ItemDto;
import com.baseline.salescore.entity.Item;
import com.baseline.salescore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    private DtoMapper mapper;
    private ItemRepository itemRepository;
    private InventoryService inventoryService;

    @Transactional
    public Item createItem(ItemDto newItem) {
        Item item = mapper.itemDtoToItem(newItem);
        return itemRepository.save(item);
    }

    @Transactional
    public List<ItemDto> getAllItems() {
        List<ItemDto> items = mapper.itemsToDto(itemRepository.findAll());
        Map<Long, Integer> stock = inventoryService.getStock();
        items.forEach(i -> i.setStock(stock.get(i.getId())));
        return items;
    }

    @Autowired
    public void setMapper(DtoMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
}
