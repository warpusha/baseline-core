package com.baseline.salescore.controller;

import com.baseline.salescore.dto.ResponseDto;
import com.baseline.salescore.dto.SkuDto;
import com.baseline.salescore.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class SkuController {

    private SkuService skuService;

    @PostMapping("/sku/add")
    public ResponseDto addNewItem(@RequestBody SkuDto request) {
        log.info("Create sku request {}", request);
        SkuDto sku = skuService.createSku(request);
        log.info("Sku added to database with ID {}", sku.getId());
        return ResponseDto.ok(sku);
    }

    @GetMapping("/sku/all")
    public ResponseDto getAllSku() {
        return ResponseDto.ok(skuService.getAll());
    }

    @Autowired
    public void setSkuService(SkuService skuService) {
        this.skuService = skuService;
    }
}
