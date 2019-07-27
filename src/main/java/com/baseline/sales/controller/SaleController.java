package com.baseline.sales.controller;

import com.baseline.sales.dto.OrderDto;
import com.baseline.sales.dto.ResponseDto;
import com.baseline.sales.dto.SaleDto;
import com.baseline.sales.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
@Slf4j
public class SaleController {

    private SaleService saleService;

    @PostMapping("/sale")
    public ResponseDto sell(@RequestBody OrderDto order) {
        log.info("New order request: {}", order);
        SaleDto sell = saleService.sell(order);
        log.info("Order processed: {}", sell);
        return ResponseDto.ok(sell);
    }

    @Autowired
    public void setSaleService(SaleService saleService) {
        this.saleService = saleService;
    }
}
