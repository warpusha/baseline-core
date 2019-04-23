package com.baseline.salescore.controller;

import com.baseline.salescore.dto.OrderDto;
import com.baseline.salescore.dto.ResponseDto;
import com.baseline.salescore.dto.SaleDto;
import com.baseline.salescore.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
