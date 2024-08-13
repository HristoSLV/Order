package com.catJam.Order.order;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"test"})
@Transactional
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testSaveOrder() {
        OrderEntity order = new OrderEntity();
        order.setUserId(1L);
        order.setTotalAmount(100.50);
        order.setBookIds(List.of(101L, 102L));

        OrderEntity savedOrder = orderRepository.save(order);

        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getUserId()).isEqualTo(1L);
        assertThat(savedOrder.getTotalAmount()).isEqualTo(100.50);
        assertThat(savedOrder.getBookIds()).containsExactly(101L, 102L);
        assertThat(savedOrder.getOrderDate()).isNotNull(); // Should be automatically set
    }

    @Test
    public void testFindById() {
        OrderEntity order = new OrderEntity();
        order.setUserId(2L);
        order.setTotalAmount(200.75);
        order.setBookIds(List.of(201L, 202L));

        OrderEntity savedOrder = orderRepository.save(order);

        Optional<OrderEntity> foundOrder = orderRepository.findById(savedOrder.getId());

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getId()).isEqualTo(savedOrder.getId());
        assertThat(foundOrder.get().getUserId()).isEqualTo(2L);
        assertThat(foundOrder.get().getTotalAmount()).isEqualTo(200.75);
        assertThat(foundOrder.get().getBookIds()).containsExactly(201L, 202L);
    }

    @Test
    public void testFindAll() {
        OrderEntity order1 = new OrderEntity();
        order1.setUserId(3L);
        order1.setTotalAmount(150.00);
        order1.setBookIds(List.of(301L, 302L));

        OrderEntity order2 = new OrderEntity();
        order2.setUserId(4L);
        order2.setTotalAmount(250.00);
        order2.setBookIds(List.of(401L, 402L));

        orderRepository.save(order1);
        orderRepository.save(order2);

        List<OrderEntity> orders = orderRepository.findAll();

        assertThat(orders).hasSize(2);
        assertThat(orders.get(0).getUserId()).isEqualTo(3L);
        assertThat(orders.get(1).getUserId()).isEqualTo(4L);
    }

    @Test
    public void testDeleteOrder() {
        OrderEntity order = new OrderEntity();
        order.setUserId(5L);
        order.setTotalAmount(300.00);
        order.setBookIds(List.of(501L, 502L));

        OrderEntity savedOrder = orderRepository.save(order);

        orderRepository.deleteById(savedOrder.getId());

        Optional<OrderEntity> deletedOrder = orderRepository.findById(savedOrder.getId());

        assertThat(deletedOrder).isNotPresent();
    }
}
