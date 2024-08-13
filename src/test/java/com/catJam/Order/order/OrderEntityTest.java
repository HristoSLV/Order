package com.catJam.Order.order;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"test"})
@Transactional
public class OrderEntityTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    public void testPersistAndFindOrderEntity() {
        // Given
        List<Long> bookIds = Arrays.asList(10L, 20L, 30L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(100L);
        orderEntity.setTotalAmount(250.75);
        orderEntity.setBookIds(bookIds);

        // When
        entityManager.persist(orderEntity);
        entityManager.flush(); // Ensure the entity is persisted

        OrderEntity foundOrder = entityManager.find(OrderEntity.class, orderEntity.getId());

        // Then
        assertNotNull(foundOrder);
        assertEquals(100L, foundOrder.getUserId());
        assertEquals(250.75, foundOrder.getTotalAmount(), 0.001);
        assertNotNull(foundOrder.getOrderDate()); // Check that the @CreationTimestamp is automatically set
        assertEquals(bookIds, foundOrder.getBookIds());
    }

    @Test
    public void testUpdateOrderEntity() {
        // Given
        List<Long> bookIds = Arrays.asList(10L, 20L, 30L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(100L);
        orderEntity.setTotalAmount(250.75);
        orderEntity.setBookIds(bookIds);

        entityManager.persist(orderEntity);
        entityManager.flush();

        // When
        OrderEntity foundOrder = entityManager.find(OrderEntity.class, orderEntity.getId());
        foundOrder.setTotalAmount(300.00);
        entityManager.persist(foundOrder);
        entityManager.flush();

        OrderEntity updatedOrder = entityManager.find(OrderEntity.class, orderEntity.getId());

        // Then
        assertNotNull(updatedOrder);
        assertEquals(300.00, updatedOrder.getTotalAmount(), 0.001);
    }

    @Test
    public void testDeleteOrderEntity() {
        // Given
        List<Long> bookIds = Arrays.asList(10L, 20L, 30L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUserId(100L);
        orderEntity.setTotalAmount(250.75);
        orderEntity.setBookIds(bookIds);

        entityManager.persist(orderEntity);
        entityManager.flush();

        // When
        entityManager.remove(orderEntity);
        entityManager.flush();

        OrderEntity deletedOrder = entityManager.find(OrderEntity.class, orderEntity.getId());

        // Then
        assertNull(deletedOrder);
    }
}
