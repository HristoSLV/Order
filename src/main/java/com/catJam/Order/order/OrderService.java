package com.catJam.Order.order;

import com.catJam.Order.bookClient.BookClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookClient bookClient;

    public OrderService(OrderRepository orderRepository, BookClient bookClient) {
        this.orderRepository = orderRepository;
        this.bookClient = bookClient;
    }
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderDTO> getOrderById(Long id) {
        return orderRepository.findById(id).map(this::toDTO);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Integer availableStock = bookClient.getStock(orderDTO.getBookId()).getBody();

        if (availableStock == null || availableStock < orderDTO.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available.");
        }

        bookClient.decreaseStock(orderDTO.getBookId(), orderDTO.getQuantity());

        OrderEntity order = toEntity(orderDTO);
        order.setOrderDate(LocalDate.now());
        OrderEntity savedOrder = orderRepository.save(order);

        return toDTO(savedOrder);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO toDTO(OrderEntity order) {
        return new OrderDTO(order.getId(), order.getCustomerName(), order.getBookId(), order.getQuantity(), order.getOrderDate());
    }

    private OrderEntity toEntity(OrderDTO orderDTO) {
        return new OrderEntity(orderDTO.getId(), orderDTO.getCustomerName(), orderDTO.getBookId(), orderDTO.getQuantity(), orderDTO.getOrderDate());
    }
}
