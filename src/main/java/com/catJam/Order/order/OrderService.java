package com.catJam.Order.order;

import com.catJam.Order.bookClient.BookClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
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
        log.info("Attempting to create order: {}", orderDTO);

        // Извикване на наличността на книгата
        Integer availableStock = null;
        try {
            availableStock = bookClient.getBookById(orderDTO.getBookId()).getBody();
            log.info("Available stock for book ID {}: {}", orderDTO.getBookId(), availableStock);
        } catch (Exception e) {
            log.error("Error fetching stock for book ID {}: {}", orderDTO.getBookId(), e.getMessage());
            throw new IllegalStateException("Could not check stock availability. Please try again later.");
        }

        // Проверка за наличност
        if (availableStock == null) {
            log.error("Stock information is not available for book ID {}.", orderDTO.getBookId());
            throw new IllegalArgumentException("Stock information is not available.");
        }

        if (availableStock < orderDTO.getQuantity()) {
            log.error("Not enough stock available for book ID {}. Available: {}, Requested: {}",
                    orderDTO.getBookId(), availableStock, orderDTO.getQuantity());
            throw new IllegalArgumentException("Not enough stock available.");
        }

        // Намаляване на наличността
        try {
            bookClient.decreaseStock(orderDTO.getBookId(), orderDTO.getQuantity());
            log.info("Stock decreased successfully for book ID {} by quantity {}.", orderDTO.getBookId(), orderDTO.getQuantity());
        } catch (Exception e) {
            log.error("Error decreasing stock for book ID {}: {}", orderDTO.getBookId(), e.getMessage());
            throw new IllegalStateException("Could not decrease stock. Please try again later.");
        }

        // Създаване на поръчка
        OrderEntity order = toEntity(orderDTO);
        order.setOrderDate(LocalDate.now());
        OrderEntity savedOrder = orderRepository.save(order);

        log.info("Order created successfully: {}", savedOrder);
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
