//package com.catJam.Order.order;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
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
//public class OrderControllerIntegrationTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        // Clear existing data
//        orderRepository.deleteAll();
//    }
//
//    @Test
//    void testGetAllOrders() throws Exception {
//        // Create and save orders directly using the repository
//        OrderEntity order1 = new OrderEntity(null, 1L, 100.0, null, Arrays.asList(1L, 2L, 3L));
//        OrderEntity order2 = new OrderEntity(null, 2L, 150.0, null, Arrays.asList(4L, 5L));
//
//        orderRepository.saveAll(List.of(order1, order2));
//
//        MvcResult result = mockMvc.perform(get("/order/find-all"))
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        List<OrderEntity> orders = objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(List.class, OrderEntity.class));
//
//        assertNotNull(orders);
//        assertEquals(2, orders.size());
//
//        // Get the current date
//        LocalDate today = LocalDate.now();
//
//        // Dynamically get IDs from saved entities
//        OrderEntity firstOrder = orders.get(0);
//        assertNotNull(firstOrder.getId());
//        assertEquals(100.0, firstOrder.getTotalAmount());
//        assertEquals(today, firstOrder.getOrderDate()); // Compare with today's date
//        assertIterableEquals(Arrays.asList(1L, 2L, 3L), firstOrder.getBookIds());
//
//        OrderEntity secondOrder = orders.get(1);
//        assertNotNull(secondOrder.getId());
//        assertEquals(150.0, secondOrder.getTotalAmount());
//        assertEquals(today, secondOrder.getOrderDate()); // Compare with today's date
//        assertIterableEquals(Arrays.asList(4L, 5L), secondOrder.getBookIds());
//    }
//
//    @Test
//    void testGetOrderByIdFound() throws Exception {
//        // Create and save the order, retrieve the generated ID
//        OrderEntity savedOrder = orderRepository.save(new OrderEntity(null, 1L, 100.0, null, Arrays.asList(1L, 2L, 3L)));
//
//        MvcResult result = mockMvc.perform(get("/order/find/" + savedOrder.getId()))
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        OrderEntity order = objectMapper.readValue(content, OrderEntity.class);
//
//        // Get the current date
//        LocalDate today = LocalDate.now();
//
//        assertNotNull(order);
//        assertEquals(savedOrder.getId(), order.getId());
//        assertEquals(100.0, order.getTotalAmount());
//        assertEquals(today, order.getOrderDate()); // Compare with today's date
//        assertIterableEquals(Arrays.asList(1L, 2L, 3L), order.getBookIds());
//    }
//
//    @Test
//    void testCreateOrder() throws Exception {
//        OrderEntity newOrder = new OrderEntity(null, 3L, 200.0, null, Arrays.asList(6L, 7L));
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
//        // Get the current date
//        LocalDate today = LocalDate.now();
//
//        assertNotNull(createdOrder.getId());
//        assertEquals(3L, createdOrder.getUserId());
//        assertEquals(200.0, createdOrder.getTotalAmount());
//        assertEquals(today, createdOrder.getOrderDate()); // Compare with today's date
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
//        assertEquals(createdOrder.getOrderDate(), foundOrder.getOrderDate()); // Compare with today's date
//        assertIterableEquals(createdOrder.getBookIds(), foundOrder.getBookIds());
//    }
//
//    @Test
//    void testUpdateOrder() throws Exception {
//        // Create and save the order, retrieve the generated ID
//        OrderEntity savedOrder = orderRepository.save(new OrderEntity(null, 1L, 100.0, null, Arrays.asList(1L, 2L, 3L)));
//
//        OrderEntity updatedOrder = new OrderEntity(savedOrder.getId(), 1L, 250.0, Arrays.asList(8L, 9L));
//
//        String requestBody = objectMapper.writeValueAsString(updatedOrder);
//
//        MvcResult result = mockMvc.perform(put("/order/update/" + savedOrder.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andReturn();
//
//        String content = result.getResponse().getContentAsString();
//        OrderEntity responseOrder = objectMapper.readValue(content, OrderEntity.class);
//
//        // Get the current date
//        LocalDate today = LocalDate.now();
//
//        assertNotNull(responseOrder);
//        assertEquals(savedOrder.getId(), responseOrder.getId());
//        assertEquals(250.0, responseOrder.getTotalAmount());
//        //assertEquals(today, responseOrder.getOrderDate()); // Compare with today's date
//        assertIterableEquals(Arrays.asList(8L, 9L), responseOrder.getBookIds());
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
//    void testDeleteOrder() throws Exception {
//        // Create and save the order, retrieve the generated ID
//        OrderEntity savedOrder = orderRepository.save(new OrderEntity(null, 1L, 100.0, LocalDate.of(2024, 8, 13), Arrays.asList(1L, 2L, 3L)));
//
//        MvcResult result = mockMvc.perform(delete("/order/delete/" + savedOrder.getId()))
//                .andReturn();
//
//        assertEquals(200, result.getResponse().getStatus());
//        assertEquals("Order deleted successfully!", result.getResponse().getContentAsString());
//
//        // Verify the order was actually deleted
//        MvcResult findResult = mockMvc.perform(get("/order/find/" + savedOrder.getId()))
//                .andReturn();
//        assertEquals(404, findResult.getResponse().getStatus());
//    }
//}
