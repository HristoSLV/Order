package com.catJam.Order.order;

import com.catJam.Order.bookClient.BookClient;
import com.catJam.Order.bookClient.BookModel;
import org.springframework.stereotype.Service;

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

    public OrderEntity createOrder(OrderEntity orderEntity) {
        return orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getAllOrders() {
        List<OrderEntity> orders = orderRepository.findAll();
        orders.forEach(this::populateBooks);
        return orders;
    }

    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id).map(this::populateBooks);
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

    private BookModel getBookById(Long id) {
        return bookClient.findById(id);
    }

    private OrderEntity populateBooks(OrderEntity order) {
        List<BookModel> books = order.getBookIds().stream()
                .map(this::getBookById)
                .collect(Collectors.toList());
        order.setBooks(books);
        return order;
    }
}
