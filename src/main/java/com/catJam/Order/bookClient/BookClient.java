package com.catJam.Order.bookClient;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

public interface BookClient {

    @GetExchange("/books")
    List<BookEntity> findAll();

    @GetExchange("/books/{id}")
    BookEntity findById(Long id);

    @PostExchange("/books")
    BookEntity create(BookEntity bookEntity);

    @PutExchange("/books/{id}")
    BookEntity update(@PathVariable Long id, BookEntity bookEntity);

    @DeleteMapping("/books/{id}")
    void delete(@PathVariable Long id);

    @GetExchange("/books/search")
    List<BookEntity> findBooksByAuthorAndTitle(@RequestParam String author, @RequestParam String title);

}
