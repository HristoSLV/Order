//package com.catJam.Order.order;
//
//import com.catJam.Order.bookClient.BookClient;
//import com.catJam.Order.bookClient.BookModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles({"test"})
//class OrderServiceTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private BookClient bookClient;
//
//    @InjectMocks
//    private OrderService orderService;
//
//    private OrderEntity orderEntity;
//    private BookModel book1;
//    private BookModel book2;
//
//    @BeforeEach
//    void setUp() {
//        orderEntity = new OrderEntity();
//        orderEntity.setId(1L);
//    }
//
//    @Test
//    void createOrder_ShouldSaveOrder() {
//        when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
//
//        OrderEntity savedOrder = orderService.createOrder(orderEntity);
//
//        assertThat(savedOrder).isNotNull();
//        assertThat(savedOrder.getId()).isEqualTo(1L);
//        verify(orderRepository, times(1)).save(orderEntity);
//        // No interaction with bookClient in this test
//    }
//
//    @Test
//    void getOrderById_WhenOrderNotFound_ShouldReturnEmptyOptional() {
//        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        Optional<OrderEntity> result = orderService.getOrderById(1L);
//
//        assertThat(result).isEmpty();
//        verify(orderRepository, times(1)).findById(1L);
//        // No interaction with bookClient in this test
//    }
//
//    @Test
//    void deleteOrder_ShouldDeleteOrderById() {
//        doNothing().when(orderRepository).deleteById(1L);
//
//        orderService.deleteOrder(1L);
//
//        verify(orderRepository, times(1)).deleteById(1L);
//        // No interaction with bookClient in this test
//    }
//
//    @Test
//    void updateOrder_ShouldUpdateExistingOrder() {
//        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
//        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
//
//        OrderEntity updatedOrder = new OrderEntity();
//        updatedOrder.setId(1L);
//
//        OrderEntity result = orderService.updateOrder(1L, updatedOrder);
//
//        assertThat(result).isNotNull();
//        assertThat(result.getId()).isEqualTo(1L);
//        verify(orderRepository, times(1)).findById(1L);
//        verify(orderRepository, times(1)).save(orderEntity);
//        // No interaction with bookClient in this test
//    }
//
//    @Test
//    void updateOrder_WhenOrderNotFound_ShouldThrowException() {
//        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
//
//        OrderEntity updatedOrder = new OrderEntity();
//        updatedOrder.setId(1L);
//
//        assertThatThrownBy(() -> orderService.updateOrder(1L, updatedOrder))
//                .isInstanceOf(RuntimeException.class)
//                .hasMessage("Order not found");
//
//        verify(orderRepository, times(1)).findById(1L);
//        verify(orderRepository, never()).save(any(OrderEntity.class));
//        // No interaction with bookClient in this test
//    }
//}
