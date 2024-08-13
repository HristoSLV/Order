package com.catJam.Order.order;

import com.catJam.Order.bookClient.BookModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
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
    private List<Long> bookIds;
    @Transient
    private List<BookModel> books;

    public OrderEntity(Long id, Long userId, double totalAmount, LocalDate orderDate, List<Long> bookIds) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.bookIds = bookIds;
    }

    public OrderEntity(Long id, Long userId, double totalAmount, List<Long> bookIds) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.bookIds = bookIds;
    }
}
