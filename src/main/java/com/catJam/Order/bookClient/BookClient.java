package com.catJam.Order.bookClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@FeignClient(name = "BookService", url = "${book.service.url}")
public interface BookClient {
    @GetMapping("/books/{id}/stock")
    ResponseEntity<Integer> getBookById(@PathVariable Long id);
    @PutMapping("/books/{id}/reduce-stock")
    ResponseEntity<String> decreaseStock(@PathVariable("id") Long bookId, @RequestParam("quantity") Integer quantity);
}
