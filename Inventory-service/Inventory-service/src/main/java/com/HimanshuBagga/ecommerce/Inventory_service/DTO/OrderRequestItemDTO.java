package com.HimanshuBagga.ecommerce.Inventory_service.DTO;

import lombok.Data;

@Data
public class OrderRequestItemDTO {
    private Long productId;
    private Integer quantity;
}
