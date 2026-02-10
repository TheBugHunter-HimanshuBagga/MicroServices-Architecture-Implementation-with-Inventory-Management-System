package com.HimanshuBagga.ecommerce.Inventory_service.Service;

import com.HimanshuBagga.ecommerce.Inventory_service.DTO.ProductDto;
import com.HimanshuBagga.ecommerce.Inventory_service.entity.Product;
import com.HimanshuBagga.ecommerce.Inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory(){
        log.info("Fetching all inventories items");
        List<Product> inventories = productRepository.findAll(); // inventories
        // from here i will be returned with the List of Product Entity
        return inventories.stream()
                .map(Product -> modelMapper.map(Product , ProductDto.class))
                .toList();
    }

    public ProductDto getProductsById(Long id){
        log.info("Getting products By Id: {}", id);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product not found with id: "+ id)
        );
        return modelMapper.map(product , ProductDto.class);
    }
}
