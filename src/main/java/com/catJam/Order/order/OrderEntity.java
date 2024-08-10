package com.catJam.Order.order;

import com.catJam.Order.bookClient.BookEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEntity {
//    @EmbeddedId
//    private OrderEmbeddedId orderEmbeddedId;
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
    private List<BookEntity> books;

}
