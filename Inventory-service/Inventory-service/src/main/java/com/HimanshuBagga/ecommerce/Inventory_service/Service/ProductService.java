package com.HimanshuBagga.ecommerce.Inventory_service.Service;

import com.HimanshuBagga.ecommerce.Inventory_service.DTO.OrderRequestDTO;
import com.HimanshuBagga.ecommerce.Inventory_service.DTO.OrderRequestItemDTO;
import com.HimanshuBagga.ecommerce.Inventory_service.DTO.ProductDto;
import com.HimanshuBagga.ecommerce.Inventory_service.entity.Product;
import com.HimanshuBagga.ecommerce.Inventory_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public Double reduceStocks(OrderRequestDTO orderRequestDTO) {
        log.info("Reducing the stock");
        Double totalPrice = 0.0;
        for(OrderRequestItemDTO orderRequestItemDTO: orderRequestDTO.getItems()){
            Long productId = orderRequestItemDTO.getProductId();
            Integer quantity = orderRequestItemDTO.getQuantity();

            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new RuntimeException("Product not found with Id: " + productId)
            );

            if(product.getStock() < quantity){
                throw new RuntimeException("Product needed cannot be full filled for given quantity");
            }

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);
            totalPrice = quantity*product.getPrice();
        }
        return totalPrice;
    }
}
