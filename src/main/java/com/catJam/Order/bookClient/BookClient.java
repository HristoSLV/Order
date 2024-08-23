package com.catJam.Order.bookClient;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

public interface BookClient {

//    @GetExchange("/books")
//    List<BookModel> findAll();

    @PutExchange("/books")
    List<BookModel> updateListOfBooks(@PathVariable List<Long> ids);

    @GetExchange("/books/{id}")
    BookModel findById(@PathVariable Long id);

//    @PostExchange("/books")
//    BookModel create(BookModel bookModel);

    @PutExchange("/books/{id}")
    BookModel updateById(@PathVariable Long id);

//    @PutExchange("/books/{id}")
//    BookModel update(@PathVariable Long id, BookModel bookModel);

//    @DeleteMapping("/books/{id}")
//    void delete(@PathVariable Long id);

//    @GetExchange("/books/search")
//    List<BookModel> findBooksByAuthorAndTitle(@RequestParam String author, @RequestParam String title);

}
