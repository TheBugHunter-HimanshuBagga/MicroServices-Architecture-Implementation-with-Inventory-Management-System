package com.HimanshuBagga.ecommerce.Inventory_service.Controller;

import com.HimanshuBagga.ecommerce.Inventory_service.DTO.ProductDto;
import com.HimanshuBagga.ecommerce.Inventory_service.Service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

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
}
