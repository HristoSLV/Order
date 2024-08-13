package com.catJam.Order.order;

import com.catJam.Order.bookClient.BookClient;
import com.catJam.Order.bookClient.BookModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles({"test"})
class OrderServiceTestTwo {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookClient bookClient;

    @InjectMocks
    private OrderService orderService;

    private OrderEntity orderEntity;
    private BookModel book1;
    private BookModel book2;

    @BeforeEach
    void setUp() {
        orderEntity = new OrderEntity();
        orderEntity.setId(1L);
        orderEntity.setBookIds(Arrays.asList(1L, 2L));

        book1 = new BookModel(1L, "Book One", "Author One", 29.99, 100);
        book2 = new BookModel(2L, "Book Two", "Author Two", 39.99, 50);

        // Mocking the responses for the BookClient
        // This stub should only be set up if the tested method requires it
        when(bookClient.findById(1L)).thenReturn(book1);
        when(bookClient.findById(2L)).thenReturn(book2);
    }


    @Test
    void getAllOrders_ShouldReturnAllOrdersWithBooksPopulated() {
        List<OrderEntity> orders = Arrays.asList(orderEntity);
        when(orderRepository.findAll()).thenReturn(orders);

        List<OrderEntity> result = orderService.getAllOrders();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getBooks()).hasSize(2);

        BookModel firstBook = result.get(0).getBooks().get(0);
        assertThat(firstBook.id()).isEqualTo(1L);
        assertThat(firstBook.title()).isEqualTo("Book One");
        assertThat(firstBook.author()).isEqualTo("Author One");
        assertThat(firstBook.price()).isEqualTo(29.99);
        assertThat(firstBook.stock()).isEqualTo(100);

        BookModel secondBook = result.get(0).getBooks().get(1);
        assertThat(secondBook.id()).isEqualTo(2L);
        assertThat(secondBook.title()).isEqualTo("Book Two");
        assertThat(secondBook.author()).isEqualTo("Author Two");
        assertThat(secondBook.price()).isEqualTo(39.99);
        assertThat(secondBook.stock()).isEqualTo(50);

        verify(orderRepository, times(1)).findAll();
        verify(bookClient, times(1)).findById(1L);
        verify(bookClient, times(1)).findById(2L);
    }

    @Test
    void getOrderById_ShouldReturnOrderWithBooksPopulated() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));

        Optional<OrderEntity> result = orderService.getOrderById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getBooks()).hasSize(2);

        BookModel firstBook = result.get().getBooks().get(0);
        assertThat(firstBook.id()).isEqualTo(1L);
        assertThat(firstBook.title()).isEqualTo("Book One");
        assertThat(firstBook.author()).isEqualTo("Author One");
        assertThat(firstBook.price()).isEqualTo(29.99);
        assertThat(firstBook.stock()).isEqualTo(100);

        BookModel secondBook = result.get().getBooks().get(1);
        assertThat(secondBook.id()).isEqualTo(2L);
        assertThat(secondBook.title()).isEqualTo("Book Two");
        assertThat(secondBook.author()).isEqualTo("Author Two");
        assertThat(secondBook.price()).isEqualTo(39.99);
        assertThat(secondBook.stock()).isEqualTo(50);

        verify(orderRepository, times(1)).findById(1L);
        verify(bookClient, times(1)).findById(1L);
        verify(bookClient, times(1)).findById(2L);
    }
}
