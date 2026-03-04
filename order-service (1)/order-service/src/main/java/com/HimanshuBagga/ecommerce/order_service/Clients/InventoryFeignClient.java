package com.HimanshuBagga.ecommerce.order_service.Clients;

import com.HimanshuBagga.ecommerce.order_service.DTO.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Inventory-service" , path = "/inventory")
public interface InventoryFeignClient {

    @PutMapping("/products/reduce-stocks")
    Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto);
}
