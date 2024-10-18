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
        orderEntity.getBookQuantities().forEach((bookId, quantity) -> {
            // Взимаме книгата по ID
            BookModel book = bookClient.findById(bookId);

            // Проверка дали има достатъчно наличност
            if (book.getStock() < quantity) {
                throw new IllegalArgumentException("Not enough stock for book ID: " + bookId);
            }

            // Намаляване на наличностите
            bookClient.updateStock(bookId, quantity);
        });

        // Запазваме поръчката
        return orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getAllOrders2() {
        return orderRepository.findAll();
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
            order.setUserId(updatedOrder.getUserId());
            order.setTotalAmount(updatedOrder.getTotalAmount());
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setBookQuantities(updatedOrder.getBookQuantities());
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    private BookModel getBookById(Long id) {
        return bookClient.findById(id);
    }

    private OrderEntity populateBooks(OrderEntity order) {
        List<BookModel> books = order.getBookQuantities().keySet().stream()
                .map(this::getBookById)
                .collect(Collectors.toList());
        order.setBooks(books);
        return order;
    }

    // Примерен нов метод за търсене на книги по автор и заглавие
    public List<BookModel> searchBooks(String author, String title) {
        return bookClient.findBooksByAuthorAndTitle(author, title);
    }
}
