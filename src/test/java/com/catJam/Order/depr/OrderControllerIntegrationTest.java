//package com.catJam.Order.order;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.time.LocalDate;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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
//    private OrderRepository orderRepository; // Assuming you have a repository
//
//    @BeforeEach
//    void setUp() {
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
//        mockMvc.perform(get("/order/find-all"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].totalAmount").value(100.0))
//                .andExpect(jsonPath("$[0].orderDate").value("2024-08-13"))
//                .andExpect(jsonPath("$[0].bookIds").value(Arrays.asList(1L, 2L, 3L)))
//                .andExpect(jsonPath("$[1].id").value(2))
//                .andExpect(jsonPath("$[1].totalAmount").value(150.0))
//                .andExpect(jsonPath("$[1].orderDate").value("2024-08-14"))
//                .andExpect(jsonPath("$[1].bookIds").value(Arrays.asList(4L, 5L)));
//    }
//
//    @Test
//    void testGetOrderByIdFound() throws Exception {
//        mockMvc.perform(get("/order/find/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.totalAmount").value(100.0))
//                .andExpect(jsonPath("$.orderDate").value("2024-08-13"))
//                .andExpect(jsonPath("$.bookIds").value(Arrays.asList(1L, 2L, 3L)));
//    }
//
//    @Test
//    void testGetOrderByIdNotFound() throws Exception {
//        mockMvc.perform(get("/order/find/99"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testCreateOrder() throws Exception {
//        OrderEntity newOrder = new OrderEntity(
//                null, 3L, 200.0, LocalDate.of(2024, 8, 15), Arrays.asList(6L, 7L));
//
//        String response = mockMvc.perform(post("/order/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newOrder)))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        // Parse the response to extract the id
//        OrderEntity createdOrder = objectMapper.readValue(response, OrderEntity.class);
//
//        mockMvc.perform(get("/order/find/" + createdOrder.getId()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId").value(3))
//                .andExpect(jsonPath("$.totalAmount").value(200.0))
//                .andExpect(jsonPath("$.orderDate").value("2024-08-15"))
//                .andExpect(jsonPath("$.bookIds").value(Arrays.asList(6, 7)));
//    }
//
//    @Test
//    void testDeleteOrder() throws Exception {
//        mockMvc.perform(delete("/order/delete/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Order deleted successfully!"));
//
//        mockMvc.perform(get("/order/find/1"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void testUpdateOrder() throws Exception {
//        OrderEntity updatedOrder = new OrderEntity(
//                1L, 1L, 250.0, LocalDate.of(2024, 8, 16), Arrays.asList(8L, 9L));
//
//        mockMvc.perform(put("/order/update/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedOrder)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.totalAmount").value(250.0))
//                .andExpect(jsonPath("$.orderDate").value("2024-08-16"))
//                .andExpect(jsonPath("$.bookIds").value(Arrays.asList(8L, 9L)));
//    }
//}
