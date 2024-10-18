package com.catJam.Order.bookClient;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

public interface BookClient {

    @GetExchange("/books")
    List<BookModel> findAll();  // Махаме @PathVariable, защото няма ID

    @GetExchange("/books/{id}")
    BookModel findById(@PathVariable Long id);  // Тук ID-то е в URL-а

    @PostExchange("/books")
    BookModel create(@RequestBody BookModel bookModel);  // Трябва да използваме @RequestBody за POST

    @PutExchange("/books/{id}")
    BookModel update(@PathVariable Long id, @RequestBody BookModel bookModel);  // @RequestBody за PUT

    @DeleteMapping("/books/{id}")
    void delete(@PathVariable Long id);  // Правилно използване на @DeleteMapping с PathVariable

    @GetExchange("/books/search")
    List<BookModel> findBooksByAuthorAndTitle(@RequestParam String author, @RequestParam String title);  // Корекция на параметрите

    @PutExchange("/books/{id}/reduce-stock")
    void updateStock(@PathVariable Long id, @RequestParam Integer quantity);  // Параметрите са правилно зададени
}
