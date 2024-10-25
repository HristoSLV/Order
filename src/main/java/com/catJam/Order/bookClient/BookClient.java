package com.catJam.Order.bookClient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@FeignClient(name = "book-service", url = "${book.service.url}")
public interface BookClient {
    @GetMapping("/books/{id}/stock")
    ResponseEntity<Integer> getStock(@PathVariable("id") Long bookId);
    @PostMapping("/books/{id}/decrease-stock")
    ResponseEntity<String> decreaseStock(@PathVariable("id") Long bookId, @RequestParam("quantity") Integer quantity);
}
