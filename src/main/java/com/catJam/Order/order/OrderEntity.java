package com.catJam.Order.order;


import com.catJam.Order.bookClient.BookModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Long userId;

    private double totalAmount;

    @CreationTimestamp
    private LocalDate orderDate;

    @ElementCollection
    @CollectionTable(name = "order_books", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "book_id")
    @Column(name = "quantity")
    private Map<Long, Integer> bookQuantities;

    @Transient
    private List<BookModel>books;
}

