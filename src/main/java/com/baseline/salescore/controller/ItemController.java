package com.baseline.salescore.controller;

import com.baseline.salescore.dto.InventoryHeaderDto;
import com.baseline.salescore.dto.InventoryTransitionDto;
import com.baseline.salescore.dto.ItemDto;
import com.baseline.salescore.dto.ResponseDto;
import com.baseline.salescore.entity.Item;
import com.baseline.salescore.service.InventoryService;
import com.baseline.salescore.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class ItemController {

    private ItemService itemService;
    private InventoryService inventoryService;

    @PostMapping("/item/add")
    public ResponseDto addNewItem(@RequestBody ItemDto request) {
        log.info("Create item request {}", request);
        Item newItem = itemService.createItem(request);
        log.info("Item added to database with ID {}", newItem.getId());
        return ResponseDto.ok(newItem);
    }

    @PostMapping("/stock/update")
    public ResponseDto addStock(@RequestBody InventoryTransitionDto request) {
        log.info("Update stock request {}", request);
        InventoryHeaderDto header = inventoryService.updateStock(request);
        log.info("Stock with type {} for item with id {} is updated", header.getType(), request.getItemId());
        return ResponseDto.ok(header);
    }

    @GetMapping("/stock")
    public ResponseDto getStock() {
        return ResponseDto.ok(inventoryService.getStock());
    }

    @GetMapping("/item")
    public ResponseDto getItems() {
        return ResponseDto.ok(itemService.getAllItems());
    }

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
}
