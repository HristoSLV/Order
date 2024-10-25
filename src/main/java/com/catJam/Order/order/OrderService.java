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
            if (book == null) {
                throw new IllegalArgumentException("Book with ID: " + bookId + " not found.");
            }
            if (book.getStock() < quantity){
                throw new IllegalArgumentException("Not enough stock for book ID: "+bookId);
            }
            // Намаляване на наличностите
            bookClient.updateStock(bookId, quantity);
        });

        double totalAmount=orderEntity.getBookQuantities().entrySet().stream()
                .mapToDouble(entry ->{
                    BookModel book = bookClient.findById(entry.getKey());
                    return book.getPrice()* entry.getValue();
                })
                .sum();
        orderEntity.setTotalAmount(totalAmount);

        // Запазваме поръчката
        return orderRepository.save(orderEntity);
    }

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::populateBooks)
                .collect(Collectors.toList());
    }

    public Optional<OrderEntity> getOrderById(Long id) {
        return orderRepository.findById(id).map(this::populateBooks);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderEntity updateOrder(Long id, OrderEntity updatedOrder) {
        return orderRepository.findById(id).map(existingOrder ->{
            existingOrder.setUserId(updatedOrder.getUserId());
            existingOrder.setBookQuantities(updatedOrder.getBookQuantities());
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            return orderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }
    private OrderEntity populateBooks(OrderEntity order) {
        List<BookModel> books = order.getBookQuantities().keySet().stream()
                .map(bookId -> {
                    try {
                        return bookClient.findById(bookId);
                    }catch (Exception e){
                        System.err.println("Failed to retrieve book with ID: "+bookId+ ": "+e.getMessage());
                        return null;
                    }
                })
                .filter(book -> book!=null)
                        .collect(Collectors.toList());
        order.setBooks(books);
        return order;
    }

    // Примерен нов метод за търсене на книги по автор и заглавие
    public List<BookModel> searchBooks(String author, String title) {
        return bookClient.findBooksByAuthorAndTitle(author, title);
    }
}
