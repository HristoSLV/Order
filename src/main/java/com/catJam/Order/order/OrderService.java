package com.catJam.Order.order;


import com.catJam.Order.bookClient.BookClient;
import com.catJam.Order.bookClient.BookModel;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookClient bookClient;

    public OrderEntity createOrder2(OrderEntity orderEntity) {
        OrderEntity newOrderEntity = reduceBookStockWithList(orderEntity);
        return orderRepository.save(newOrderEntity);
    }

    public OrderEntity createOrder(OrderEntity orderEntity) {
        OrderEntity newOrderEntity = reduceBookStock(orderEntity);
        return orderRepository.save(newOrderEntity);
    }

//    public List<OrderEntity> getAllOrders2() {
//        return orderRepository.findAll();
//    }

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
            //order.setId(updatedOrder.getId());
            order.setUserId(updatedOrder.getUserId());
            order.setTotalAmount(updatedOrder.getTotalAmount());
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setBookIds(updatedOrder.getBookIds());
            //order.setBooks(updatedOrder.getBooks());
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    private BookModel getBookById(Long id) {
        return bookClient.findById(id);
    }


    private OrderEntity populateBooks(OrderEntity orderEntity) {
        List<BookModel> books = new ArrayList<>();
        for (Long bookId : orderEntity.getBookIds()) {
            books.add(getBookById(bookId));
        }
        orderEntity.setBooks(books);
        return orderEntity;
    }

    private BookModel updateBookById(Long id) {
        return bookClient.updateById(id);
    }

    private OrderEntity reduceBookStock(OrderEntity orderEntity) {
        List<BookModel> books = new ArrayList<>();
        for (Long bookId : orderEntity.getBookIds()) {
            books.add(updateBookById(bookId));
        }
        orderEntity.setBooks(books);
        return orderEntity;
    }

    private OrderEntity reduceBookStockWithList(OrderEntity orderEntity) {
        List<BookModel> books = bookClient.updateListOfBooks(orderEntity.getBookIds());
        orderEntity.setBooks(books);
        return orderEntity;
    }
}
