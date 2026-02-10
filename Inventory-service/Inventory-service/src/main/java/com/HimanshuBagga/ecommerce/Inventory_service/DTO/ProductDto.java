package com.HimanshuBagga.ecommerce.Inventory_service.DTO;

import jakarta.persistence.Id;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private Double price;
    private Integer stock;
}
