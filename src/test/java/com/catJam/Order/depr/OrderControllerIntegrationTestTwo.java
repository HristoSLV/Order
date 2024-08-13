//package com.catJam.Order.order;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//@SpringBootTest
//@Transactional
//@ActiveProfiles({"test"})
//@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//public class OrderControllerIntegrationTestTwo {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private OrderRepository orderRepository; // Assuming you have a repository
//
//    @BeforeEach
//    void setUp() throws Exception {
//        // Clear existing data
//        orderRepository.deleteAll();
//
//        // Set up test data
//        OrderEntity order1 = new OrderEntity(
//                1L, 1L, 100.0, LocalDate.of(2024, 8, 13), Arrays.asList(1L, 2L, 3L));
//        OrderEntity order2 = new OrderEntity(
//                2L, 2L, 150.0, LocalDate.of(2024, 8, 14), Arrays.asList(4L, 5L));
//
//        orderRepository.saveAll(List.of(order1, order2));
//    }
//
//    @Test
//    void testGetAllOrders() throws Exception {
//        MvcResult result = mockMvc.perform(get("/order/find-all"))
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        List<OrderEntity> orders = objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, OrderEntity.class));
//
//        assertNotNull(orders);
//        assertEquals(2, orders.size());
//
//        OrderEntity firstOrder = orders.get(0);
//        assertEquals(1L, firstOrder.getId());
//        assertEquals(100.0, firstOrder.getTotalAmount());
//        assertEquals(LocalDate.of(2024, 8, 13), firstOrder.getOrderDate());
//        assertIterableEquals(Arrays.asList(1L, 2L, 3L), firstOrder.getBookIds());
//
//        OrderEntity secondOrder = orders.get(1);
//        assertEquals(2L, secondOrder.getId());
//        assertEquals(150.0, secondOrder.getTotalAmount());
//        assertEquals(LocalDate.of(2024, 8, 14), secondOrder.getOrderDate());
//        assertIterableEquals(Arrays.asList(4L, 5L), secondOrder.getBookIds());
//    }
//
//    @Test
//    void testGetOrderByIdFound() throws Exception {
//        MvcResult result = mockMvc.perform(get("/order/find/1"))
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        OrderEntity order = objectMapper.readValue(content, OrderEntity.class);
//
//        assertNotNull(order);
//        assertEquals(1L, order.getId());
//        assertEquals(100.0, order.getTotalAmount());
//        assertEquals(LocalDate.of(2024, 8, 13), order.getOrderDate());
//        assertIterableEquals(Arrays.asList(1L, 2L, 3L), order.getBookIds());
//    }
//
//    @Test
//    void testGetOrderByIdNotFound() throws Exception {
//        MvcResult result = mockMvc.perform(get("/order/find/99"))
//                .andReturn();
//
//        assertEquals(404, result.getResponse().getStatus());
//    }
//
//    @Test
//    void testCreateOrder() throws Exception {
//        OrderEntity newOrder = new OrderEntity(
//                null, 3L, 200.0, LocalDate.of(2024, 8, 21), Arrays.asList(6L, 7L));
//
//        String requestBody = objectMapper.writeValueAsString(newOrder);
//
//        MvcResult result = mockMvc.perform(post("/order/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        OrderEntity createdOrder = objectMapper.readValue(content, OrderEntity.class);
//
//        assertNotNull(createdOrder.getId());
//        assertEquals(3L, createdOrder.getUserId());
//        assertEquals(200.0, createdOrder.getTotalAmount());
//        assertEquals(LocalDate.of(2024, 8, 21), createdOrder.getOrderDate());
//        assertIterableEquals(Arrays.asList(6L, 7L), createdOrder.getBookIds());
//
//        // Verify the order was actually created
//        MvcResult findResult = mockMvc.perform(get("/order/find/" + createdOrder.getId()))
//                .andReturn();
//        String findContent = findResult.getResponse().getContentAsString();
//        OrderEntity foundOrder = objectMapper.readValue(findContent, OrderEntity.class);
//
//        assertEquals(createdOrder.getId(), foundOrder.getId());
//        assertEquals(createdOrder.getUserId(), foundOrder.getUserId());
//        assertEquals(createdOrder.getTotalAmount(), foundOrder.getTotalAmount());
//        assertEquals(createdOrder.getOrderDate(), foundOrder.getOrderDate());
//        assertIterableEquals(createdOrder.getBookIds(), foundOrder.getBookIds());
//    }
//
//    @Test
//    void testDeleteOrder() throws Exception {
//        MvcResult result = mockMvc.perform(delete("/order/delete/1"))
//                .andReturn();
//
//        assertEquals(200, result.getResponse().getStatus());
//        assertEquals("Order deleted successfully!", result.getResponse().getContentAsString());
//
//        // Verify the order was actually deleted
//        MvcResult findResult = mockMvc.perform(get("/order/find/1"))
//                .andReturn();
//        assertEquals(404, findResult.getResponse().getStatus());
//    }
//
//    @Test
//    void testUpdateOrder() throws Exception {
//        OrderEntity updatedOrder = new OrderEntity(
//                1L, 1L, 250.0, LocalDate.of(2024, 8, 16), Arrays.asList(8L, 9L));
//
//        String requestBody = objectMapper.writeValueAsString(updatedOrder);
//
//        MvcResult result = mockMvc.perform(put("/order/update/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        OrderEntity responseOrder = objectMapper.readValue(content, OrderEntity.class);
//
//        assertNotNull(responseOrder);
//        assertEquals(1L, responseOrder.getId());
//        assertEquals(250.0, responseOrder.getTotalAmount());
//        assertEquals(LocalDate.of(2024, 8, 16), responseOrder.getOrderDate());
//        assertIterableEquals(Arrays.asList(8L, 9L), responseOrder.getBookIds());
//    }
//}
