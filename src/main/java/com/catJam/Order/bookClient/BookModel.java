package com.catJam.Order.bookClient;

public record BookModel(Long id,
                        String title,
                        String author,
                        Double price,
                        Integer stock) {
}
