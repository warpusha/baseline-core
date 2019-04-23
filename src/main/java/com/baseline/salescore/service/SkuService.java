package com.baseline.salescore.service;

import com.baseline.salescore.converter.DtoMapper;
import com.baseline.salescore.dto.CompositionDto;
import com.baseline.salescore.dto.SkuDto;
import com.baseline.salescore.entity.Composition;
import com.baseline.salescore.entity.Item;
import com.baseline.salescore.entity.Sku;
import com.baseline.salescore.exception.InvalidRequestException;
import com.baseline.salescore.exception.ItemNotFoundException;
import com.baseline.salescore.repository.ItemRepository;
import com.baseline.salescore.repository.SkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkuService {

    private SkuRepository skuRepository;
    private ItemRepository itemRepository;
    private DtoMapper dtoMapper;

//    public SkuDto createSku(String name, String description, Map<Item, Integer> composition) {
//        Sku sku = Sku.builder().name(name).description(description).build();
//        List<Composition> compositions = new ArrayList<>();
//        composition.forEach((item, quantity) -> {
//            compositions.add(Composition.builder().item(item).quantity(quantity).sku(sku).build());
//        });
//        sku.setCompositions(compositions);
//        return dtoMapper.skuToDto(skuRepository.save(sku));
//    }

    @Transactional
    public List<SkuDto> getAll() {
        return dtoMapper.skusToDto(skuRepository.findAll());
    }

    @Transactional
    public SkuDto createSku(SkuDto request) {
        validateSkuRequest(request);
        Map<Long, Integer> composition = request.getCompositions().stream()
                .collect(Collectors.toMap(c -> c.getItem().getId(), CompositionDto::getQuantity));
        Sku sku = Sku.builder().retailPrice(BigDecimal.valueOf(request.getRetailPrice()))
                .name(request.getName()).description(request.getDescription()).build();
        composition.forEach((itemId, quantity) -> {
            Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException(itemId));
            sku.getCompositions().add(Composition.builder().item(item).quantity(quantity).sku(sku).build());
        });
        return dtoMapper.skuToDto(skuRepository.save(sku));
    }

    private void validateSkuRequest(SkuDto request) {
        Set<Long> itemIds = request.getCompositions().stream().map(c -> c.getItem().getId()).collect(Collectors.toSet());
        if (StringUtils.isEmpty(request.getName())) {
            throw new InvalidRequestException("Blank name", request);
        } else if (request.getRetailPrice() == null) {
            throw new InvalidRequestException("Blank retail price", request);
        } else if (request.getCompositions().stream().anyMatch(c -> c.getQuantity() == 0)) {
            throw new InvalidRequestException("Invalid quantity", request);
        } else if (itemIds.size() != request.getCompositions().size()) {
            throw new InvalidRequestException("Duplicate item ids", request);
        }
    }

    @Autowired
    public void setSkuRepository(SkuRepository skuRepository) {
        this.skuRepository = skuRepository;
    }

    @Autowired
    public void setDtoMapper(DtoMapper dtoMapper) {
        this.dtoMapper = dtoMapper;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
}
