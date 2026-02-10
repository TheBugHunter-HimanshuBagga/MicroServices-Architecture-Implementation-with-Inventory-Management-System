package com.HimanshuBagga.ecommerce.order_service.DTO;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long id;
    private Long productId;
    private Integer quantity;
}
