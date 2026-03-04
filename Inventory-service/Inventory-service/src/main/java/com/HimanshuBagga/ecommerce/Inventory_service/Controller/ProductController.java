package com.HimanshuBagga.ecommerce.Inventory_service.Controller;

import com.HimanshuBagga.ecommerce.Inventory_service.Clients.OrdersFeignClient;
import com.HimanshuBagga.ecommerce.Inventory_service.DTO.OrderRequestDTO;
import com.HimanshuBagga.ecommerce.Inventory_service.DTO.ProductDto;
import com.HimanshuBagga.ecommerce.Inventory_service.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/fetchOrders")
    public String fetchFromOrdersService(){
//        ServiceInstance orderService = discoveryClient.getInstances("order-service").getFirst();

        return ordersFeignClient.HelloOrders();

//        Without Feign Client
//        String response = restClient.get()
//                .uri(orderService.getUri()+"/orders/core/helloOrder")
//                .retrieve()
//                .body(String.class);
//        return response;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory(){
        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductsById(@RequestParam Long id){
        ProductDto inventory = productService.getProductsById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStock(@RequestBody OrderRequestDTO orderRequestDTO){
        Double totalPrice = productService.reduceStocks(orderRequestDTO);
        return ResponseEntity.ok(totalPrice);
    }

}
