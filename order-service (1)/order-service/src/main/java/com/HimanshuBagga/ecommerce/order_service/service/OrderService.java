package com.HimanshuBagga.ecommerce.order_service.service;

import com.HimanshuBagga.ecommerce.order_service.DTO.OrderRequestDto;
import com.HimanshuBagga.ecommerce.order_service.DTO.OrderRequestItemDto;
import com.HimanshuBagga.ecommerce.order_service.Repository.OrderRepository;
import com.HimanshuBagga.ecommerce.order_service.entity.Orders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public List<OrderRequestDto> getAllOrder(){
        log.info("Fetching all the Orders");
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(
                order -> modelMapper.map(order , OrderRequestDto.class)
        ).toList();
    }

    public OrderRequestDto getOrderById(Long id){
        log.info("Getting the Order By Id: {} ", id);
        Orders order = orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Order not found")
        );
        return modelMapper.map(order , OrderRequestDto.class);
    }

}
