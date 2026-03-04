package com.HimanshuBagga.ecommerce.Inventory_service.DTO;

import lombok.Data;

import java.util.List;
@Data
public class OrderRequestDTO {
    private List<OrderRequestItemDTO> items;
}
