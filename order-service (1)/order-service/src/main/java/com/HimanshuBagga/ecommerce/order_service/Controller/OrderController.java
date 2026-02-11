package com.HimanshuBagga.ecommerce.order_service.Controller;

import com.HimanshuBagga.ecommerce.order_service.DTO.OrderRequestDto;
import com.HimanshuBagga.ecommerce.order_service.Repository.OrderRepository;
import com.HimanshuBagga.ecommerce.order_service.entity.Orders;
import com.HimanshuBagga.ecommerce.order_service.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @GetMapping("/helloOrders")
    public String helloOrders(){
        return "Hello from Order Service";
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
}
