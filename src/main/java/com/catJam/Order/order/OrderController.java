package com.catJam.Order.order;

import com.catJam.Order.bookClient.BookModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/find-all")
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<OrderEntity> getOrderById(@PathVariable Long id) {
        Optional<OrderEntity> orderEntity = orderService.getOrderById(id);
        return orderEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<OrderEntity> newOrder(@RequestBody OrderEntity orderEntity) {
        OrderEntity order = orderService.createOrder(orderEntity);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderEntity> updateOrder(@PathVariable Long id, @RequestBody OrderEntity orderEntity) {
        OrderEntity order = orderService.updateOrder(id, orderEntity);
        return ResponseEntity.ok(order);
    }

    // Нов метод за търсене на книги по автор и заглавие
    @GetMapping("/books/search")
    public ResponseEntity<List<BookModel>> searchBooks(@RequestParam String author, @RequestParam String title) {
        List<BookModel> books = orderService.searchBooks(author, title);
        return ResponseEntity.ok(books);
    }
}
