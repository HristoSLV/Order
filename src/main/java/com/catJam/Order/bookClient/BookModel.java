package com.catJam.Order.bookClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookModel {
    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
}
