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
@Getter
@Setter
@RequiredArgsConstructor
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
    @ManyToMany
    @JoinTable(
            name = "order_book",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    List<BookEntity> orderedBooks;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "userId = " + userId + ", " +
                "totalAmount = " + totalAmount + ", " +
                "orderDate = " + orderDate + ")";
    }
}
