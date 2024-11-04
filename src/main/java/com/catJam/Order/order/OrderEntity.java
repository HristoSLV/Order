package com.catJam.Order.order;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity
@Table(name = "order_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private LocalDate orderDate;
}

