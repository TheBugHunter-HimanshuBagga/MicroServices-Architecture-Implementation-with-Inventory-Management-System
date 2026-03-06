package com.HimanshuBagga.ecommerce.order_service.service;

import com.HimanshuBagga.ecommerce.order_service.Clients.InventoryFeignClient;
import com.HimanshuBagga.ecommerce.order_service.DTO.OrderRequestDto;
import com.HimanshuBagga.ecommerce.order_service.DTO.OrderRequestItemDto;
import com.HimanshuBagga.ecommerce.order_service.Repository.OrderRepository;
import com.HimanshuBagga.ecommerce.order_service.entity.OrderItem;
import com.HimanshuBagga.ecommerce.order_service.entity.OrderStatus;
import com.HimanshuBagga.ecommerce.order_service.entity.Orders;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
    private final InventoryFeignClient inventoryFeignClient;

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

    @Retry(name = "inventoryRetry" , fallbackMethod = "createOrderFallback")
    @RateLimiter(name = "inventoryRateLimiter" , fallbackMethod = "createOrderFallback") // if called more than the limit in a particular time then the method will return a  fallback
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        // calling Inventory Microservices
        log.info("Calling craeteOrder");
        Double totalPrice = inventoryFeignClient.reduceStocks(orderRequestDto);
        // here we are doing an openfeign client impl for inter microservice communication but to make it more stable and resilent to any kind of error
        Orders orders = modelMapper.map(orderRequestDto , Orders.class);
        for(OrderItem orderItem:orders.getItem()){
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        Orders savedOrders = orderRepository.save(orders);
        return modelMapper.map(savedOrders , OrderRequestDto.class);
    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto, Throwable throwable){
        log.error("Fallback ouccered due to : {}", throwable.getMessage());

        return new OrderRequestDto();
    }
}
