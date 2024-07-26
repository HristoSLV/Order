package com.catJam.Order.bookClient;

public record BookEntity(Long id,
                         String title,
                         String author,
                         Double price,
                         Integer stock) {
}
