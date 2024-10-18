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
    private List<BookModel> books;

    public OrderEntity(Long id, Long userId, double totalAmount, LocalDate orderDate, Map<Long, Integer> bookQuantities) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.bookQuantities = bookQuantities;
    }

    public OrderEntity(Long id, Long userId, double totalAmount, Map<Long, Integer> bookQuantities) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.bookQuantities = bookQuantities;
    }
}

