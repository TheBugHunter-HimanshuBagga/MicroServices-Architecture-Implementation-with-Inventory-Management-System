package com.HimanshuBagga.ecommerce.order_service.Repository;

import com.HimanshuBagga.ecommerce.order_service.entity.OrderItem;
import com.HimanshuBagga.ecommerce.order_service.entity.Orders;
import org.hibernate.query.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders , Long> {

}
