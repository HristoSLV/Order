package com.catJam.Order.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEmbeddedId implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    private List<String> bookNames;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEmbeddedId that = (OrderEmbeddedId) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(bookNames, that.bookNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, bookNames);
    }
}
