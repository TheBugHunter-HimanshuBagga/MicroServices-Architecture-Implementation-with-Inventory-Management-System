package com.HimanshuBagga.ecommerce.order_service.Controller;

import com.HimanshuBagga.ecommerce.order_service.Clients.InventoryFeignClient;
import com.HimanshuBagga.ecommerce.order_service.DTO.OrderRequestDto;
import com.HimanshuBagga.ecommerce.order_service.DTO.OrderRequestItemDto;
import com.HimanshuBagga.ecommerce.order_service.Repository.OrderRepository;
import com.HimanshuBagga.ecommerce.order_service.entity.Orders;
import com.HimanshuBagga.ecommerce.order_service.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Order;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @GetMapping("/helloOrders")
    public String helloOrders(@RequestHeader("X-User-Id") Long userId){
        return "Hello from Order Service , user Id : " + userId;
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(){
        log.info("Fetching All orders via controller");
        List<OrderRequestDto> orderRequestDto = orderService.getAllOrder();
        return ResponseEntity.ok(orderRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@RequestParam Long id){
        log.info("Fetching order with Id : {} ", id);
        OrderRequestDto orders = orderService.getOrderById(id);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/Create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        OrderRequestDto orderRequestDto1 = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);
    }
}
