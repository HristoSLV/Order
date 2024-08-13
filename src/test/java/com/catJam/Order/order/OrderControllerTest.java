package com.catJam.Order.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles({"test"})
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testGetAllOrders() throws Exception {
        // Given
        OrderEntity order1 = new OrderEntity(1L, 1L, 100.0, null, null, null);
        OrderEntity order2 = new OrderEntity(2L, 2L, 150.0, null, null, null);
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        // When & Then
        mockMvc.perform(get("/order/find-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testGetOrderByIdFound() throws Exception {
        // Given
        OrderEntity order = new OrderEntity(1L, 1L, 100.0, null, null, null);
        when(orderService.getOrderById(anyLong())).thenReturn(Optional.of(order));

        // When & Then
        mockMvc.perform(get("/order/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetOrderByIdNotFound() throws Exception {
        // Given
        when(orderService.getOrderById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/order/find/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOrder() throws Exception {
        // Given
        OrderEntity order = new OrderEntity(1L, 1L, 100.0, null, null, null);
        when(orderService.createOrder(any(OrderEntity.class))).thenReturn(order);

        // When & Then
        mockMvc.perform(post("/order/create")
                        .contentType("application/json")
                        .content("{\"id\":1,\"userId\":1,\"totalAmount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteOrder() throws Exception {
        // When & Then
        mockMvc.perform(delete("/order/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order deleted successfully!"));

        verify(orderService, times(1)).deleteOrder(anyLong());
    }

    @Test
    void testUpdateOrder() throws Exception {
        // Given
        OrderEntity updatedOrder = new OrderEntity(1L, 1L, 100.0, null, null, null);
        when(orderService.updateOrder(anyLong(), any(OrderEntity.class))).thenReturn(updatedOrder);

        // When & Then
        mockMvc.perform(put("/order/update/1")
                        .contentType("application/json")
                        .content("{\"id\":1,\"userId\":1,\"totalAmount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}
