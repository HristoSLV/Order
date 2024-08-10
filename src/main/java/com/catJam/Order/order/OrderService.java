package com.catJam.Order.order;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderEntity createOrder(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderEntity updateOrder(Long id, OrderEntity updatedOrder) {
        Optional<OrderEntity> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            OrderEntity order = existingOrder.get();
            order.setId(updatedOrder.getId());
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }


}
